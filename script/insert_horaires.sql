-- Insertion des horaires de travail (sans caractères spéciaux)

INSERT INTO horaire_travail (actif,libelle, heure_debut, heure_fin, duree_pause, heure_pause_debut, heure_pause_fin, tolerance_retard, couleur) VALUES
(true,'Equipe Matin (8h-17h)', '08:00:00', '17:00:00', 60, '12:00:00', '13:00:00', 15, '#10B981'),
(true,'Equipe Apres-midi (14h-22h)', '14:00:00', '22:00:00', 30, '18:00:00', '18:30:00', 10, '#F59E0B'),
(true,'Equipe Nuit (22h-6h)', '22:00:00', '06:00:00', 30, '02:00:00', '02:30:00', 10, '#6366F1'),
(true,'Horaire Flexible (9h-18h)', '09:00:00', '18:00:00', 60, '12:30:00', '13:30:00', 30, '#8B5CF6');
