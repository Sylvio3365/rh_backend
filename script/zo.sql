-- ============================================
-- MODULE : GESTION DU TEMPS ET DES PRÉSENCES
-- Base : PostgreSQL
-- ============================================

-- Connexion à la base de données
\c rh_database;

-- ============================================
-- 1. TABLE : horaire_travail
-- ============================================

CREATE TABLE horaire_travail(
   id_horaire SERIAL PRIMARY KEY,
   libelle VARCHAR(100) NOT NULL,
   heure_debut TIME NOT NULL,
   heure_fin TIME NOT NULL,
   duree_pause INT NOT NULL DEFAULT 60,          -- En minutes
   heure_pause_debut TIME,
   heure_pause_fin TIME,
   tolerance_retard INT DEFAULT 15,              -- Minutes de tolérance
   couleur VARCHAR(20) DEFAULT '#3B82F6',        -- Couleur pour affichage frontend
   actif BOOLEAN DEFAULT TRUE
);

-- Index
CREATE INDEX idx_horaire_actif ON horaire_travail(actif);

-- ============================================
-- 2. TABLE : pointage
-- ============================================

CREATE TABLE pointage(
   id_pointage SERIAL PRIMARY KEY,
   id_personnel INT NOT NULL,
   date_heure TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   type_pointage VARCHAR(20) NOT NULL,           -- 'ENTREE', 'SORTIE', 'PAUSE_DEBUT', 'PAUSE_FIN'
   methode VARCHAR(20) DEFAULT 'QR_CODE',        -- 'QR_CODE', 'MANUEL', 'BADGE'
   id_horaire INT,                               -- Horaire choisi lors du scan
   localisation VARCHAR(100),
   remarque TEXT,
   FOREIGN KEY(id_personnel) REFERENCES personnel(id_personnel) ON DELETE CASCADE,
   FOREIGN KEY(id_horaire) REFERENCES horaire_travail(id_horaire) ON DELETE SET NULL
);

-- Index
CREATE INDEX idx_pointage_personnel_date ON pointage(id_personnel, date_heure);
CREATE INDEX idx_pointage_date ON pointage(date_heure);
CREATE INDEX idx_pointage_type ON pointage(type_pointage);

-- ============================================
-- 3. TABLE : presence_journaliere
-- ============================================

CREATE TABLE presence_journaliere(
   id_presence_jour SERIAL PRIMARY KEY,
   id_personnel INT NOT NULL,
   date_ DATE NOT NULL,
   heure_arrivee TIME,
   heure_depart TIME,
   duree_pause_reel INT DEFAULT 0,               -- En minutes
   heures_travaillees DECIMAL(5,2) DEFAULT 0.00,
   minutes_retard INT DEFAULT 0,
   heures_supplementaires DECIMAL(5,2) DEFAULT 0.00,
   statut VARCHAR(20) DEFAULT 'EN_COURS',        -- 'EN_COURS', 'PRESENT', 'RETARD', 'ABSENT', 'CONGE'
   id_horaire INT,
   observation TEXT,
   UNIQUE(id_personnel, date_),
   FOREIGN KEY(id_personnel) REFERENCES personnel(id_personnel) ON DELETE CASCADE,
   FOREIGN KEY(id_horaire) REFERENCES horaire_travail(id_horaire) ON DELETE SET NULL
);

-- Index
CREATE INDEX idx_presence_date ON presence_journaliere(date_);
CREATE INDEX idx_presence_personnel ON presence_journaliere(id_personnel);
CREATE INDEX idx_presence_statut ON presence_journaliere(statut);

-- ============================================
-- 4. TABLE : heure_sup
-- ============================================

-- Supprimer l'ancienne table si elle existe
DROP TABLE IF EXISTS heure_sup CASCADE;

-- Créer la table avec la nouvelle structure
CREATE TABLE heure_sup(
   id_heure_sup SERIAL PRIMARY KEY,
   id_personnel INT NOT NULL,
   date_ DATE NOT NULL,
   nb_heure DECIMAL(5,2) NOT NULL,
   pourcentage DECIMAL(5,2) NOT NULL,            -- 30, 50, 100, 130
   id_presence_jour INT,                         -- Lien optionnel vers journée
   FOREIGN KEY(id_personnel) REFERENCES personnel(id_personnel) ON DELETE CASCADE,
   FOREIGN KEY(id_presence_jour) REFERENCES presence_journaliere(id_presence_jour) ON DELETE CASCADE
);

-- Index
CREATE INDEX idx_heure_sup_personnel_date ON heure_sup(id_personnel, date_);
CREATE INDEX idx_heure_sup_date ON heure_sup(date_);
CREATE INDEX idx_heure_sup_pourcentage ON heure_sup(pourcentage);

-- ============================================
-- 5. MODIFICATION TABLE : personnnel_contrat
-- Ajouter le champ id_horaire
-- ============================================

ALTER TABLE personnnel_contrat 
ADD COLUMN id_horaire INT;

ALTER TABLE personnnel_contrat
ADD FOREIGN KEY(id_horaire) REFERENCES horaire_travail(id_horaire) ON DELETE SET NULL;

-- ============================================
-- 6. FONCTION STOCKÉE : consolider_presence_jour
-- ============================================

CREATE OR REPLACE FUNCTION consolider_presence_jour(
    p_id_personnel INT, 
    p_date DATE
) RETURNS VOID AS $$
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
    v_id_presence_jour INT;
    v_pourcentage_hs DECIMAL(5,2);
    v_total_heures_semaine DECIMAL(5,2);
BEGIN
    -- Récupérer les pointages de la journée
    SELECT 
        MIN(CASE WHEN type_pointage='ENTREE' THEN date_heure::TIME END),
        MAX(CASE WHEN type_pointage='SORTIE' THEN date_heure::TIME END),
        COALESCE(
            EXTRACT(EPOCH FROM (
                MAX(CASE WHEN type_pointage='PAUSE_FIN' THEN date_heure END) - 
                MIN(CASE WHEN type_pointage='PAUSE_DEBUT' THEN date_heure END)
            ))::INT / 60, 
            0
        ),
        MAX(id_horaire)
    INTO v_heure_arrivee, v_heure_depart, v_duree_pause, v_id_horaire
    FROM pointage
    WHERE id_personnel = p_id_personnel 
      AND date_heure::DATE = p_date;
    
    -- Si pas de pointage d'entrée, sortir
    IF v_heure_arrivee IS NULL THEN
        RETURN;
    END IF;
    
    -- Calculer heures travaillées
    IF v_heure_depart IS NOT NULL THEN
        v_heures_travaillees := ROUND(
            (EXTRACT(EPOCH FROM (v_heure_depart - v_heure_arrivee))::INT / 60 - v_duree_pause) / 60.0, 
            2
        );
    ELSE
        v_heures_travaillees := 0;
        v_statut := 'EN_COURS';
    END IF;
    
    -- Récupérer l'horaire de référence
    IF v_id_horaire IS NOT NULL THEN
        SELECT heure_debut, heure_fin, duree_pause, tolerance_retard
        INTO v_heure_debut_theorique, v_heure_fin_theorique, v_duree_pause_theorique, v_tolerance
        FROM horaire_travail
        WHERE id_horaire = v_id_horaire;
        
        -- Durée théorique en heures
        v_duree_theorique := ROUND(
            (EXTRACT(EPOCH FROM (v_heure_fin_theorique - v_heure_debut_theorique))::INT / 60 - v_duree_pause_theorique) / 60.0,
            2
        );
        
        -- Calculer retard
        v_minutes_retard := GREATEST(0, 
            EXTRACT(EPOCH FROM (v_heure_arrivee - v_heure_debut_theorique))::INT / 60 - v_tolerance
        );
        
        -- Calculer heures supplémentaires
        IF v_heure_depart IS NOT NULL THEN
            v_heures_sup := GREATEST(0, v_heures_travaillees - v_duree_theorique);
        ELSE
            v_heures_sup := 0;
        END IF;
        
        -- Déterminer statut
        IF v_heure_depart IS NOT NULL THEN
            IF v_minutes_retard > 0 THEN
                v_statut := 'RETARD';
            ELSE
                v_statut := 'PRESENT';
            END IF;
        END IF;
    ELSE
        v_minutes_retard := 0;
        v_heures_sup := 0;
        v_duree_theorique := 8.00; -- Par défaut
    END IF;
    
    -- Insérer ou mettre à jour presence_journaliere
    INSERT INTO presence_journaliere (
        id_personnel, date_, heure_arrivee, heure_depart, 
        duree_pause_reel, heures_travaillees, minutes_retard, 
        heures_supplementaires, statut, id_horaire
    ) VALUES (
        p_id_personnel, p_date, v_heure_arrivee, v_heure_depart,
        v_duree_pause, v_heures_travaillees, v_minutes_retard,
        v_heures_sup, v_statut, v_id_horaire
    )
    ON CONFLICT (id_personnel, date_) DO UPDATE SET
        heure_arrivee = EXCLUDED.heure_arrivee,
        heure_depart = EXCLUDED.heure_depart,
        duree_pause_reel = EXCLUDED.duree_pause_reel,
        heures_travaillees = EXCLUDED.heures_travaillees,
        minutes_retard = EXCLUDED.minutes_retard,
        heures_supplementaires = EXCLUDED.heures_supplementaires,
        statut = EXCLUDED.statut,
        id_horaire = EXCLUDED.id_horaire;
    
    -- Récupérer l'id_presence_jour
    SELECT id_presence_jour INTO v_id_presence_jour
    FROM presence_journaliere
    WHERE id_personnel = p_id_personnel AND date_ = p_date;
    
    -- Si heures sup > 0 ET c'est un jour de semaine normal (Lundi=1 à Vendredi=5 en PostgreSQL)
    IF v_heures_sup > 0 AND v_heure_depart IS NOT NULL 
       AND EXTRACT(DOW FROM p_date) BETWEEN 1 AND 5 THEN
        
        -- Calculer total heures dans la semaine (pour déterminer le pourcentage)
        SELECT COALESCE(SUM(heures_travaillees), 0) INTO v_total_heures_semaine
        FROM presence_journaliere
        WHERE id_personnel = p_id_personnel
          AND EXTRACT(WEEK FROM date_) = EXTRACT(WEEK FROM p_date)
          AND EXTRACT(YEAR FROM date_) = EXTRACT(YEAR FROM p_date)
          AND date_ < p_date;
        
        -- Déterminer le pourcentage selon les heures cumulées
        -- Jusqu'à 48h/semaine = 30%, au-delà = 50%
        IF (v_total_heures_semaine + v_heures_travaillees) <= 48 THEN
            v_pourcentage_hs := 30.00;
        ELSE
            v_pourcentage_hs := 50.00;
        END IF;
        
        -- Insérer dans heure_sup
        INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour)
        VALUES (p_id_personnel, p_date, v_heures_sup, v_pourcentage_hs, v_id_presence_jour)
        ON CONFLICT (id_personnel, date_) DO UPDATE SET 
            nb_heure = EXCLUDED.nb_heure,
            pourcentage = EXCLUDED.pourcentage
        WHERE heure_sup.id_presence_jour = v_id_presence_jour;
    END IF;
    
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 7. DONNÉES DE TEST : Horaires de travail
-- ============================================
INSERT INTO horaire_travail (libelle, heure_debut, heure_fin, duree_pause, heure_pause_debut, heure_pause_fin, tolerance_retard, couleur) VALUES
('Equipe Matin (8h-17h)', '08:00:00', '17:00:00', 60, '12:00:00', '13:00:00', 15, '#10B981'),
('Equipe Apres-midi (14h-22h)', '14:00:00', '22:00:00', 30, '18:00:00', '18:30:00', 10, '#F59E0B'),
('Equipe Nuit (22h-6h)', '22:00:00', '06:00:00', 30, '02:00:00', '02:30:00', 10, '#6366F1'),
('Horaire Flexible (9h-18h)', '09:00:00', '18:00:00', 60, '12:30:00', '13:30:00', 30, '#8B5CF6');

-- ============================================
-- 8. DONNÉES DE TEST : Pointages (Semaine exemple)
-- ============================================

-- Lundi 6 janvier 2025 - Employé 1
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(1, '2025-01-06 08:05:00', 'ENTREE', 1, 'QR_CODE'),
(1, '2025-01-06 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(1, '2025-01-06 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(1, '2025-01-06 19:00:00', 'SORTIE', 1, 'QR_CODE');  -- 2h sup

-- Mardi 7 janvier 2025 - Employé 1
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(1, '2025-01-07 08:00:00', 'ENTREE', 1, 'QR_CODE'),
(1, '2025-01-07 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(1, '2025-01-07 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(1, '2025-01-07 18:30:00', 'SORTIE', 1, 'QR_CODE');  -- 1.5h sup

-- Mercredi 8 janvier 2025 - Employé 1
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(1, '2025-01-08 08:00:00', 'ENTREE', 1, 'QR_CODE'),
(1, '2025-01-08 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(1, '2025-01-08 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(1, '2025-01-08 20:00:00', 'SORTIE', 1, 'QR_CODE');  -- 3h sup

-- Jeudi 9 janvier 2025 - Employé 1
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(1, '2025-01-09 08:00:00', 'ENTREE', 1, 'QR_CODE'),
(1, '2025-01-09 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(1, '2025-01-09 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(1, '2025-01-09 17:00:00', 'SORTIE', 1, 'QR_CODE');  -- Normal

-- Vendredi 10 janvier 2025 - Employé 1
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(1, '2025-01-10 08:00:00', 'ENTREE', 1, 'QR_CODE'),
(1, '2025-01-10 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(1, '2025-01-10 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(1, '2025-01-10 18:00:00', 'SORTIE', 1, 'QR_CODE');  -- 1h sup

-- Lundi 6 janvier 2025 - Employé 2
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(2, '2025-01-06 08:00:00', 'ENTREE', 1, 'QR_CODE'),
(2, '2025-01-06 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(2, '2025-01-06 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(2, '2025-01-06 20:00:00', 'SORTIE', 1, 'QR_CODE');  -- 3h sup

-- Mardi 7 janvier 2025 - Employé 2
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(2, '2025-01-07 08:00:00', 'ENTREE', 1, 'QR_CODE'),
(2, '2025-01-07 12:00:00', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(2, '2025-01-07 13:00:00', 'PAUSE_FIN', 1, 'QR_CODE'),
(2, '2025-01-07 21:00:00', 'SORTIE', 1, 'QR_CODE');  -- 4h sup

-- ============================================
-- 9. CONSOLIDATION DES POINTAGES
-- ============================================

SELECT consolider_presence_jour(1, '2025-01-06');
SELECT consolider_presence_jour(1, '2025-01-07');
SELECT consolider_presence_jour(1, '2025-01-08');
SELECT consolider_presence_jour(1, '2025-01-09');
SELECT consolider_presence_jour(1, '2025-01-10');

SELECT consolider_presence_jour(2, '2025-01-06');
SELECT consolider_presence_jour(2, '2025-01-07');

-- ============================================
-- 10. HEURES SUP SPÉCIALES (Week-end, nuit, férié)
-- ============================================

-- Samedi 11 janvier - Travail dimanche (100%)
INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour) VALUES
(1, '2025-01-11', 6.00, 100.00, NULL),
(2, '2025-01-11', 8.00, 100.00, NULL);

-- Dimanche 12 janvier (100%)
INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour) VALUES
(1, '2025-01-12', 4.00, 100.00, NULL),
(2, '2025-01-12', 6.00, 100.00, NULL);

-- Travail de nuit (50%)
INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour) VALUES
(3, '2025-01-06', 2.00, 50.00, NULL),
(3, '2025-01-07', 3.00, 50.00, NULL);

-- Nuit + Dimanche (130%)
INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour) VALUES
(3, '2025-01-11', 5.00, 130.00, NULL);

-- Jour férié (100%)
INSERT INTO heure_sup (id_personnel, date_, nb_heure, pourcentage, id_presence_jour) VALUES
(1, '2025-01-26', 8.00, 100.00, NULL),
(2, '2025-01-26', 7.50, 100.00, NULL);

-- ============================================
-- 11. VUES UTILES
-- ============================================

-- Vue : Résumé mensuel par employé
CREATE OR REPLACE VIEW v_resume_presence_mensuel AS
SELECT 
    p.id_personnel,
    p.nom || ' ' || p.prenom AS employe,
    EXTRACT(YEAR FROM pj.date_)::INT AS annee,
    EXTRACT(MONTH FROM pj.date_)::INT AS mois,
    COUNT(DISTINCT pj.date_) AS jours_travailles,
    SUM(pj.heures_travaillees) AS total_heures_travaillees,
    SUM(pj.minutes_retard) AS total_minutes_retard,
    SUM(pj.heures_supplementaires) AS total_heures_sup_calculees,
    COUNT(CASE WHEN pj.statut = 'RETARD' THEN 1 END) AS nb_retards,
    COUNT(CASE WHEN pj.statut = 'ABSENT' THEN 1 END) AS nb_absences
FROM personnel p
LEFT JOIN presence_journaliere pj ON p.id_personnel = pj.id_personnel
GROUP BY p.id_personnel, p.nom, p.prenom, EXTRACT(YEAR FROM pj.date_), EXTRACT(MONTH FROM pj.date_);

-- Vue : Détail heures sup
CREATE OR REPLACE VIEW v_detail_heures_sup AS
SELECT 
    hs.id_heure_sup,
    p.id_personnel,
    p.nom || ' ' || p.prenom AS employe,
    hs.date_,
    hs.nb_heure,
    hs.pourcentage,
    CASE hs.pourcentage
        WHEN 30 THEN 'Heures sup 30%'
        WHEN 50 THEN 'Heures sup 50% ou Nuit'
        WHEN 100 THEN 'Dimanche/Férié'
        WHEN 130 THEN 'Nuit + Dimanche/Férié'
        ELSE 'Autre (' || hs.pourcentage || '%)'
    END AS type_heure_sup,
    hs.id_presence_jour,
    CASE 
        WHEN hs.id_presence_jour IS NOT NULL THEN 'Journée normale'
        ELSE 'Jour spécial (week-end/férié/nuit)'
    END AS origine
FROM heure_sup hs
JOIN personnel p ON hs.id_personnel = p.id_personnel;

-- Vue : Calcul montant heures sup (base 173.33h/mois)
CREATE OR REPLACE VIEW v_montant_heures_sup AS
SELECT 
    hs.id_personnel,
    p.nom || ' ' || p.prenom AS employe,
    EXTRACT(YEAR FROM hs.date_)::INT AS annee,
    EXTRACT(MONTH FROM hs.date_)::INT AS mois,
    pc.salaire AS salaire_base,
    ROUND(pc.salaire / 173.33, 2) AS taux_horaire,
    SUM(hs.nb_heure) AS total_heures,
    SUM(
        ROUND(
            hs.nb_heure * (pc.salaire / 173.33) * (1 + hs.pourcentage / 100),
            2
        )
    ) AS montant_total_ar
FROM heure_sup hs
JOIN personnel p ON hs.id_personnel = p.id_personnel
JOIN personnnel_contrat pc ON p.id_personnel = pc.id_personnel
WHERE pc.date_fin IS NULL OR pc.date_fin > CURRENT_DATE
GROUP BY hs.id_personnel, p.nom, p.prenom, EXTRACT(YEAR FROM hs.date_), EXTRACT(MONTH FROM hs.date_), pc.salaire;

-- ============================================
-- 12. REQUÊTES DE VÉRIFICATION
-- ============================================

-- Vérifier les horaires
SELECT * FROM horaire_travail;

-- Vérifier les pointages du jour
SELECT 
    pt.id_pointage,
    p.nom || ' ' || p.prenom AS employe,
    TO_CHAR(pt.date_heure, 'YYYY-MM-DD HH24:MI:SS') AS date_heure,
    pt.type_pointage,
    h.libelle AS horaire
FROM pointage pt
JOIN personnel p ON pt.id_personnel = p.id_personnel
LEFT JOIN horaire_travail h ON pt.id_horaire = h.id_horaire
ORDER BY pt.date_heure DESC
LIMIT 20;

-- Vérifier les présences consolidées
SELECT 
    pj.id_presence_jour,
    p.nom || ' ' || p.prenom AS employe,
    pj.date_,
    pj.heure_arrivee,
    pj.heure_depart,
    pj.heures_travaillees,
    pj.heures_supplementaires,
    pj.minutes_retard,
    pj.statut
FROM presence_journaliere pj
JOIN personnel p ON pj.id_personnel = p.id_personnel
ORDER BY pj.date_ DESC, p.nom
LIMIT 20;

-- Vérifier les heures sup
SELECT * FROM v_detail_heures_sup ORDER BY date_ DESC;

-- Résumé mensuel
SELECT * FROM v_resume_presence_mensuel 
WHERE mois = EXTRACT(MONTH FROM CURRENT_DATE)
  AND annee = EXTRACT(YEAR FROM CURRENT_DATE);

-- Montants heures sup
SELECT * FROM v_montant_heures_sup 
WHERE mois = EXTRACT(MONTH FROM CURRENT_DATE)
  AND annee = EXTRACT(YEAR FROM CURRENT_DATE);

-- ============================================
-- FIN DU SCRIPT
-- ============================================


INSERT INTO type_contrat(libelle, nb_mois, renouvellement)
values ('Contrat d essaie', 6, 1),
       ('CDD', 24, 0),
       ('CDI', 0, 0);

INSERT INTO type_document (libelle)
values ('CNI'),
       ('CV'),
       ('Attestation');

INSERT INTO departement (libelle)
values ('RH'),
       ('Finance');

-- Insertion de plusieurs types de congés
INSERT INTO type_conge (libelle, nb_max)
VALUES ('maternité', 90),
       ('paternité', 15),
       ('annuel', 30),
       ('maladie', 60),
       ('formation', 20),
       ('exceptionnel', 10);


INSERT INTO Irsa (min_, max_, taux)
VALUES (0, 350000, 0),
       (350001, 400000, 5),
       (400001, 500000, 10),
       (500001, 600000, 15),
       (600001, 4000000, 20),
       (4000001, null, 25);

INSERT INTO cnaps (taux_entreprise, taux_salarie, libelle)
VALUES (13, 1, 'Taux normal'),
       (8, 1, 'Taux secteur agricole');

INSERT INTO prime (taux, nb_annee)
VALUES (5, 3),
       (10, 5),
       (15, 10),
       (20, 15),
       (25, 20),
       (30, 25);


-- Insertion des genres
INSERT INTO genre (libelle)
VALUES ('Homme'),
       ('Femme');

-- Insertion des catégories de personnel
INSERT INTO categorie_personnel (libelle)
VALUES ('Employés'), ('Cadres'),('Dirigeants');



INSERT INTO utilisateur (id_utilisateur, username, password) VALUES
(1, 'rakoto', 'test');

INSERT INTO personnel (
    id_personnel,
    dtn,
    statut,
    nom,
    prenom,
    photo,
    id_utilisateur,
    id_genre,
    id_categorie_personnel
) VALUES
-- Employé 1 : Rakoto Jean (Homme, Cadre)
(
    1,
    '1990-05-15',
    1,  -- statut actif
    'Rakoto',
    'Jean',
    'photo_rakoto_jean.jpg',
    1,
    1,  -- Homme
    1   -- Cadre
);
