-- ============================================
-- Script d'initialisation automatique Hibernate
-- Exécuté au démarrage si spring.jpa.hibernate.ddl-auto=update
-- ============================================

-- 1. Ajouter les contraintes uniques nécessaires
ALTER TABLE presence_journaliere DROP CONSTRAINT IF EXISTS unique_personnel_date;
ALTER TABLE presence_journaliere ADD CONSTRAINT unique_personnel_date UNIQUE (id_personnel, date_);

ALTER TABLE heure_sup DROP CONSTRAINT IF EXISTS unique_hs_personnel_date;
ALTER TABLE heure_sup ADD CONSTRAINT unique_hs_personnel_date UNIQUE (id_personnel, date_);

-- 2. Créer la fonction de consolidation
CREATE OR REPLACE FUNCTION consolider_presence_jour(p_id_personnel BIGINT, p_date DATE) 
RETURNS VOID AS $$
DECLARE
    v_heure_arrivee TIME;
    v_heure_depart TIME;
    v_duree_pause INT;
    v_heures_travaillees DECIMAL(5,2);
    v_id_horaire INT;
    v_heure_debut_theorique TIME;
    v_heure_fin_theorique TIME;
    v_duree_pause_theorique INT;
    v_duree_theorique DECIMAL(5,2);
    v_tolerance INT;
    v_minutes_retard INT;
    v_heures_sup DECIMAL(5,2);
    v_statut VARCHAR(20);
    v_id_presence_jour BIGINT;
    v_pourcentage_hs DECIMAL(5,2);
    v_total_heures_semaine DECIMAL(5,2);
BEGIN
    SELECT MIN(CASE WHEN type_pointage='ENTREE' THEN date_heure::TIME END), MAX(CASE WHEN type_pointage='SORTIE' THEN date_heure::TIME END), COALESCE(EXTRACT(EPOCH FROM (MAX(CASE WHEN type_pointage='PAUSE_FIN' THEN date_heure END) - MIN(CASE WHEN type_pointage='PAUSE_DEBUT' THEN date_heure END)))::INT / 60, 0), MAX(id_horaire) INTO v_heure_arrivee, v_heure_depart, v_duree_pause, v_id_horaire FROM pointage WHERE id_personnel = p_id_personnel AND date_heure::DATE = p_date;
    IF v_heure_arrivee IS NULL THEN RETURN; END IF;
    IF v_heure_depart IS NOT NULL THEN v_heures_travaillees := ROUND((EXTRACT(EPOCH FROM (v_heure_depart - v_heure_arrivee))::INT / 60 - v_duree_pause) / 60.0, 2); ELSE v_heures_travaillees := 0; v_statut := 'EN_COURS'; END IF;
    IF v_id_horaire IS NOT NULL THEN SELECT heure_debut, heure_fin, duree_pause, tolerance_retard INTO v_heure_debut_theorique, v_heure_fin_theorique, v_duree_pause_theorique, v_tolerance FROM horaire_travail WHERE id_horaire = v_id_horaire; v_duree_theorique := ROUND((EXTRACT(EPOCH FROM (v_heure_fin_theorique - v_heure_debut_theorique))::INT / 60 - v_duree_pause_theorique) / 60.0, 2); v_minutes_retard := GREATEST(0, EXTRACT(EPOCH FROM (v_heure_arrivee - v_heure_debut_theorique))::INT / 60 - v_tolerance); IF v_heure_depart IS NOT NULL THEN v_heures_sup := GREATEST(0, v_heures_travaillees - v_duree_theorique); ELSE v_heures_sup := 0; END IF; IF v_heure_depart IS NOT NULL THEN IF v_minutes_retard > 0 THEN v_statut := 'RETARD'; ELSE v_statut := 'PRESENT'; END IF; END IF; ELSE v_minutes_retard := 0; v_heures_sup := 0; v_duree_theorique := 8.00; END IF;
    INSERT INTO presence_journaliere (id_personnel, date_, heure_arrivee, heure_depart, duree_pause_reel, heures_travaillees, minutes_retard, heures_supplementaires, statut, id_horaire) VALUES (p_id_personnel, p_date, v_heure_arrivee, v_heure_depart, v_duree_pause, v_heures_travaillees, v_minutes_retard, v_heures_sup, v_statut, v_id_horaire) ON CONFLICT (id_personnel, date_) DO UPDATE SET heure_arrivee = EXCLUDED.heure_arrivee, heure_depart = EXCLUDED.heure_depart, duree_pause_reel = EXCLUDED.duree_pause_reel, heures_travaillees = EXCLUDED.heures_travaillees, minutes_retard = EXCLUDED.minutes_retard, heures_supplementaires = EXCLUDED.heures_supplementaires, statut = EXCLUDED.statut, id_horaire = EXCLUDED.id_horaire;
    SELECT id_presence_jour INTO v_id_presence_jour FROM presence_journaliere WHERE id_personnel = p_id_personnel AND date_ = p_date;
    IF v_heures_sup > 0 AND v_heure_depart IS NOT NULL AND EXTRACT(DOW FROM p_date) BETWEEN 1 AND 5 THEN SELECT COALESCE(SUM(heures_travaillees), 0) INTO v_total_heures_semaine FROM presence_journaliere WHERE id_personnel = p_id_personnel AND EXTRACT(WEEK FROM date_) = EXTRACT(WEEK FROM p_date) AND EXTRACT(YEAR FROM date_) = EXTRACT(YEAR FROM p_date) AND date_ < p_date; IF (v_total_heures_semaine + v_heures_travaillees) <= 48 THEN v_pourcentage_hs := 30.00; ELSE v_pourcentage_hs := 50.00; END IF; INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour) VALUES (p_id_personnel, p_date, v_heures_sup, v_pourcentage_hs, v_id_presence_jour) ON CONFLICT (id_personnel, date_) DO UPDATE SET nb_heure = EXCLUDED.nb_heure, pourcentage = EXCLUDED.pourcentage WHERE heure_sup.id_presence_jour = v_id_presence_jour; END IF;
END;
$$ LANGUAGE plpgsql;

-- 3. Créer les vues utiles
CREATE OR REPLACE VIEW v_resume_presence_mensuel AS SELECT p.id_personnel, p.nom || ' ' || p.prenom AS employe, EXTRACT(YEAR FROM pj.date_)::INT AS annee, EXTRACT(MONTH FROM pj.date_)::INT AS mois, COUNT(DISTINCT pj.date_) AS jours_travailles, SUM(pj.heures_travaillees) AS total_heures_travaillees, SUM(pj.minutes_retard) AS total_minutes_retard, SUM(pj.heures_supplementaires) AS total_heures_sup_calculees, COUNT(CASE WHEN pj.statut = 'RETARD' THEN 1 END) AS nb_retards, COUNT(CASE WHEN pj.statut = 'ABSENT' THEN 1 END) AS nb_absences FROM personnel p LEFT JOIN presence_journaliere pj ON p.id_personnel = pj.id_personnel GROUP BY p.id_personnel, p.nom, p.prenom, EXTRACT(YEAR FROM pj.date_), EXTRACT(MONTH FROM pj.date_);

CREATE OR REPLACE VIEW v_detail_heures_sup AS SELECT hs.id_heure_sup, p.id_personnel, p.nom || ' ' || p.prenom AS employe, hs.date_, hs.nb_heure, hs.pourcentage, CASE hs.pourcentage WHEN 30 THEN 'Heures sup 30%' WHEN 50 THEN 'Heures sup 50% ou Nuit' WHEN 100 THEN 'Dimanche/Férié' WHEN 130 THEN 'Nuit + Dimanche/Férié' ELSE 'Autre (' || hs.pourcentage || '%)' END AS type_heure_sup, hs.id_presence_jour, CASE WHEN hs.id_presence_jour IS NOT NULL THEN 'Journée normale' ELSE 'Jour spécial (week-end/férié/nuit)' END AS origine FROM heure_sup hs JOIN personnel p ON hs.id_personnel = p.id_personnel;

CREATE OR REPLACE VIEW v_montant_heures_sup AS SELECT hs.id_personnel, p.nom || ' ' || p.prenom AS employe, EXTRACT(YEAR FROM hs.date_)::INT AS annee, EXTRACT(MONTH FROM hs.date_)::INT AS mois, pc.salaire AS salaire_base, ROUND(pc.salaire / 173.33, 2) AS taux_horaire, SUM(hs.nb_heure) AS total_heures, SUM(ROUND(hs.nb_heure * (pc.salaire / 173.33) * (1 + hs.pourcentage / 100), 2)) AS montant_total_ar FROM heure_sup hs JOIN personnel p ON hs.id_personnel = p.id_personnel JOIN personnnel_contrat pc ON p.id_personnel = pc.id_personnel WHERE pc.date_fin IS NULL OR pc.date_fin > CURRENT_DATE GROUP BY hs.id_personnel, p.nom, p.prenom, EXTRACT(YEAR FROM hs.date_), EXTRACT(MONTH FROM hs.date_), pc.salaire;
