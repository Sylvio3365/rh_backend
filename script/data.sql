-- Insertion des types de contrat
INSERT INTO
    type_contrat(libelle, nb_mois, renouvellement)
VALUES
    ('Contrat d essai', 6, 1),
    ('CDD', 24, 0),
    ('CDI', 0, 0);

-- Insertion des types de document
INSERT INTO
    type_document (libelle)
VALUES
    ('CNI'),
    ('CV'),
    ('Attestation');

-- Insertion des départements
INSERT INTO
    departement (libelle)
VALUES
    ('RH'),
    ('Finance'),
    ('IT'),
    ('Commercial'),
    ('Production');

-- Insertion des types de congés
INSERT INTO
    type_conge (libelle, nb_max)
VALUES
    ('maternité', 90),
    ('paternité', 15),
    ('annuel', 30),
    ('maladie', 60),
    ('formation', 20),
    ('exceptionnel', 10);

-- Insertion des tranches d'IRSA
INSERT INTO
    Irsa (min_, max_, taux)
VALUES
    (0, 350000, 0),
    (350001, 400000, 5),
    (400001, 500000, 10),
    (500001, 600000, 15),
    (600001, 4000000, 20),
    (4000001, NULL, 25);

-- Insertion des taux CNAPS
INSERT INTO
    cnaps (taux_entreprise, taux_salarie, libelle)
VALUES
    (13, 1, 'Taux normal'),
    (8, 1, 'Taux secteur agricole');

-- Insertion des primes
INSERT INTO
    prime (taux, nb_annee)
VALUES
    (5, 3),
    (10, 5),
    (15, 10),
    (20, 15),
    (25, 20),
    (30, 25);

-- Insertion des genres
INSERT INTO
    genre (libelle)
VALUES
    ('Homme'),
    ('Femme');
INSERT INTO categorie_personnel (libelle)
VALUES ('Employés'), ('Cadres'),('Dirigeants');
INSERT INTO
    categorie_personnel (libelle)
VALUES
    ('Dirigeants'),
    ('Employés'),
    ('Cadres');

-- Insertion des postes
INSERT INTO
    poste (nom, id_departement)
VALUES
    ('Directeur RH', 1),
    ('Responsable Paie', 1),
    ('Analyste Financier', 2),
    ('Comptable', 2),
    ('Développeur', 3),
    ('Chef de Projet IT', 3),
    ('Commercial', 4),
    ('Responsable Ventes', 4),
    ('Ouvrier', 5),
    ('Technicien', 5);

-- Insertion des personnels (20 personnels avec âges et sexes variés)
INSERT INTO
    personnel (
        dtn,
        statut,
        nom,
        prenom,
        photo,
        id_genre,
        id_categorie_personnel
    )
VALUES
    -- Dirigeants (3)
    (
        '1975-05-15',
        1,
        'Rakoto',
        'Jean',
        'jean.jpg',
        1,
        1
    ),
    (
        '1978-08-22',
        1,
        'Rabe',
        'Marie',
        'marie.jpg',
        2,
        1
    ),
    (
        '1980-03-10',
        1,
        'Randria',
        'Pierre',
        'pierre.jpg',
        1,
        1
    ),
    -- Cadres (7)
    (
        '1985-11-12',
        1,
        'Rasoa',
        'Sophie',
        'sophie.jpg',
        2,
        3
    ),
    (
        '1988-07-25',
        1,
        'Rajaona',
        'Thomas',
        'thomas.jpg',
        1,
        3
    ),
    (
        '1990-02-14',
        1,
        'Randriana',
        'Alice',
        'alice.jpg',
        2,
        3
    ),
    (
        '1992-09-30',
        1,
        'Rakotobe',
        'Marc',
        'marc.jpg',
        1,
        3
    ),
    (
        '1995-12-05',
        1,
        'Razafy',
        'Julie',
        'julie.jpg',
        2,
        3
    ),
    (
        '1987-04-18',
        1,
        'Ramanana',
        'David',
        'david.jpg',
        1,
        3
    ),
    (
        '1993-06-22',
        1,
        'Ravoay',
        'Sarah',
        'sarah.jpg',
        2,
        3
    ),
    -- Employés (10)
    (
        '1998-01-15',
        1,
        'Rakotondrabe',
        'Lucas',
        'lucas.jpg',
        1,
        2
    ),
    (
        '2000-03-20',
        1,
        'Rasolofo',
        'Emma',
        'emma.jpg',
        2,
        2
    ),
    (
        '1997-08-12',
        1,
        'Randriamampianina',
        'Kevin',
        'kevin.jpg',
        1,
        2
    ),
    (
        '1999-11-08',
        1,
        'Razanakoto',
        'Chloé',
        'chloe.jpg',
        2,
        2
    ),
    (
        '1996-05-25',
        1,
        'Rakotoarisoa',
        'Nicolas',
        'nicolas.jpg',
        1,
        2
    ),
    (
        '2001-07-14',
        1,
        'Rafaralahy',
        'Léa',
        'lea.jpg',
        2,
        2
    ),
    (
        '1994-10-30',
        1,
        'Rajaonarivelo',
        'Antoine',
        'antoine.jpg',
        1,
        2
    ),
    (
        '2002-02-28',
        1,
        'Razafindrakoto',
        'Manon',
        'manon.jpg',
        2,
        2
    ),
    (
        '1995-09-17',
        1,
        'Rakotomalala',
        'Alexandre',
        'alexandre.jpg',
        1,
        2
    ),
    (
        '1998-12-03',
        1,
        'Rasamoelina',
        'Camille',
        'camille.jpg',
        2,
        2
    );

-- Insertion des contrats pour chaque personnel
INSERT INTO
    personnnel_contrat (
        id_poste,
        id_personnel,
        date_debut,
        date_fin,
        salaire,
        id_type_contrat
    )
VALUES
    -- Dirigeants (CDI)
    (1, 1, '2020-01-15', NULL, 5000000, 3),
    (8, 2, '2019-03-01', NULL, 4500000, 3),
    (6, 3, '2021-06-10', NULL, 4800000, 3),
    -- Cadres (mélange CDI et CDD)
    (5, 4, '2022-01-10', NULL, 2500000, 3),
    (6, 5, '2023-03-15', '2025-03-15', 2800000, 2),
    (5, 6, '2021-11-20', NULL, 2600000, 3),
    (7, 7, '2023-08-01', '2024-08-01', 2200000, 2),
    (8, 8, '2022-09-05', NULL, 2700000, 3),
    (5, 9, '2023-01-20', '2023-07-20', 1800000, 1),
    (7, 10, '2022-12-01', NULL, 2400000, 3),
    -- Employés (mélange de tous les types)
    (9, 11, '2023-02-10', '2023-08-10', 800000, 1),
    (10, 12, '2023-04-15', '2025-04-15', 950000, 2),
    (9, 13, '2022-07-01', NULL, 850000, 3),
    (10, 14, '2023-05-20', '2023-11-20', 750000, 1),
    (9, 15, '2023-03-01', '2025-03-01', 900000, 2),
    (10, 16, '2022-10-15', NULL, 880000, 3),
    (9, 17, '2023-06-10', '2023-12-10', 820000, 1),
    (10, 18, '2023-01-05', '2025-01-05', 920000, 2),
    (9, 19, '2022-08-20', NULL, 870000, 3),
    (10, 20, '2023-07-01', '2024-01-01', 780000, 1);

-- Insertion des utilisateurs (garder admin/admin pour le premier)
INSERT INTO
    utilisateur (username, password, id_personnel)
VALUES
    ('admin', 'admin', 1),
    ('marie.rabe', 'pass456', 2),
    ('pierre.randria', 'pass789', 3),
    ('sophie.rasoa', 'sophie123', 4),
    ('thomas.rajaona', 'thomas123', 5),
    ('alice.randriana', 'alice123', 6),
    ('marc.rakotobe', 'marc123', 7),
    ('julie.razafy', 'julie123', 8),
    ('david.ramanana', 'david123', 9),
    ('sarah.ravoay', 'sarah123', 10);

