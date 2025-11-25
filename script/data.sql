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
    ('Finance');

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

-- Insertion des catégories de personnel
INSERT INTO
    categorie_personnel (libelle)
VALUES
    ('Dirigeants'),
    ('Employés'),
    ('Cadres');

-- Insertion des personnels
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
    (
        '1990-05-15',
        1,
        'Rakoto',
        'Jean',
        'jean.jpg',
        1,
        1
    ),
    (
        '1985-08-22',
        1,
        'Rabe',
        'Marie',
        'marie.jpg',
        2,
        2
    ),
    (
        '1992-03-10',
        1,
        'Randria',
        'Pierre',
        'pierre.jpg',
        1,
        3
    );

-- Insertion des utilisateurs
INSERT INTO
    utilisateur (username, password, id_personnel)
VALUES
    ('admin', 'admin', 1),
    ('marie.rabe', 'pass456', 2),
    ('pierre.randria', 'pass789', 3);