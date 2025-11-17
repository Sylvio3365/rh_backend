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
VALUES ('Employés');

INSERT INTO categorie_personnel (libelle)
VALUES ('Cadres');

INSERT INTO categorie_personnel (libelle)
VALUES ('Dirigeants');