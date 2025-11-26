-- Script de test pour créer une présence complète

-- 1. Nettoyer les données de test
DELETE FROM heure_sup WHERE id_personnel = 1;
DELETE FROM presence_journaliere WHERE id_personnel = 1;
DELETE FROM pointage WHERE id_personnel = 1;

-- 2. Créer des pointages pour aujourd'hui
INSERT INTO pointage (id_personnel, date_heure, type_pointage, id_horaire, methode) VALUES
(1, CURRENT_TIMESTAMP - INTERVAL '9 hours', 'ENTREE', 1, 'QR_CODE'),
(1, CURRENT_TIMESTAMP - INTERVAL '5 hours', 'PAUSE_DEBUT', 1, 'QR_CODE'),
(1, CURRENT_TIMESTAMP - INTERVAL '4 hours', 'PAUSE_FIN', 1, 'QR_CODE'),
(1, CURRENT_TIMESTAMP - INTERVAL '1 hour', 'SORTIE', 1, 'QR_CODE');

-- 3. Consolider la journée
SELECT consolider_presence_jour(1, CURRENT_DATE);

-- 4. Vérifier les résultats
SELECT 'Pointages:' as type;
SELECT 
    id_pointage,
    type_pointage,
    TO_CHAR(date_heure, 'HH24:MI:SS') as heure
FROM pointage 
WHERE id_personnel = 1 
  AND date_heure::DATE = CURRENT_DATE
ORDER BY date_heure;

SELECT 'Presence:' as type;
SELECT 
    date_,
    heure_arrivee,
    heure_depart,
    heures_travaillees,
    minutes_retard,
    heures_supplementaires,
    statut
FROM presence_journaliere 
WHERE id_personnel = 1 
  AND date_ = CURRENT_DATE;

SELECT 'Heures sup:' as type;
SELECT 
    date_,
    nb_heure,
    pourcentage
FROM heure_sup 
WHERE id_personnel = 1 
  AND date_ = CURRENT_DATE;
