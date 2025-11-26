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


-- Pour le test
INSERT INTO poste (nom, id_departement)
VALUES
    ('Développeuse Frontend Senior', 1),
    ('Analyste Data', 2),
    ('Responsable Data', 2);
    
-- Poste 1 : Développeuse Frontend - RH (Premier poste)
UPDATE personnnel_contrat
SET date_fin = '2023-04-01'
WHERE id_poste = 1 AND id_personnel = 1;
-- Poste 2 : Développeuse Frontend Senior - RH (PROMOTION)
INSERT INTO personnnel_contrat (id_poste, id_personnel, date_debut, date_fin, salaire, id_type_contrat)
VALUES (2, 1, '2023-04-02', '2024-06-30', 1800000, 2);

-- Poste 3 : Analyste Data - Finance (MOBILITE)
INSERT INTO personnnel_contrat (id_poste, id_personnel, date_debut, date_fin, salaire, id_type_contrat)
VALUES (3, 1, '2024-07-01', '2025-02-15', 2000000, 2);

-- Poste 4 : Responsable Data - Finance (PROMOTION)
INSERT INTO personnnel_contrat (id_poste, id_personnel, date_debut, date_fin, salaire, id_type_contrat)
VALUES (4, 1, '2025-02-16', NULL, 2500000, 3);
