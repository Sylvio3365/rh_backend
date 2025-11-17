-- create  database  rhdb;

CREATE TABLE categorie_personnel
(
    id_categorie_personnel SERIAL,
    libelle                VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_categorie_personnel)
);

CREATE TABLE type_contrat
(
    id_type_contrat SERIAL,
    libelle         VARCHAR(50) NOT NULL,
    nb_mois         INTEGER     NOT NULL,
    renouvellement  INTEGER     NOT NULL,
    PRIMARY KEY (id_type_contrat)
);


CREATE TABLE type_document
(
    id_type_document SERIAL,
    libelle          VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_type_document)
);

CREATE TABLE departement(
   id_departement SERIAL,
   libelle VARCHAR(150)  NOT NULL,
   PRIMARY KEY(id_departement)
);


CREATE TABLE type_conge
(
    id_type_conge SERIAL,
    libelle       VARCHAR(50),
    nb_max        INTEGER,
    PRIMARY KEY (id_type_conge)
);

CREATE TABLE genre
(
    id_genre SERIAL,
    libelle  VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_genre),
    UNIQUE (libelle)
);

CREATE TABLE Irsa
(
    id_irsa SERIAL,
    min_    NUMERIC(15, 2) NOT NULL,
    max_    NUMERIC(15, 2),
    taux    NUMERIC(15, 2) NOT NULL,
    PRIMARY KEY (id_irsa)
);

CREATE TABLE cnaps
(
    id_cnaps        SERIAL,
    taux_entreprise NUMERIC(15, 2) NOT NULL,
    taux_salarie    NUMERIC(15, 2) NOT NULL,
    libelle         VARCHAR(50)    NOT NULL,
    PRIMARY KEY (id_cnaps)
);

CREATE TABLE prime
(
    id_prime SERIAL,
    taux     NUMERIC(15, 2) NOT NULL,
    nb_annee INTEGER        NOT NULL,
    PRIMARY KEY (id_prime)
);

CREATE TABLE utilisateur
(
    id_utilisateur SERIAL,
    username       VARCHAR(50) NOT NULL,
    password       VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_utilisateur),
    UNIQUE (username),
    UNIQUE (password)
);

CREATE TABLE personnel
(
    id_personnel           SERIAL,
    dtn                    DATE         NOT NULL,
    statut                 INTEGER      NOT NULL,
    nom                    VARCHAR(150) NOT NULL,
    prenom                 VARCHAR(50)  NOT NULL,
    photo                  VARCHAR(150) NOT NULL,
    id_utilisateur         INTEGER,
    id_genre               INTEGER      NOT NULL,
    id_categorie_personnel INTEGER      NOT NULL,
    PRIMARY KEY (id_personnel),
    UNIQUE (id_utilisateur),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id_utilisateur),
    FOREIGN KEY (id_genre) REFERENCES genre (id_genre),
    FOREIGN KEY (id_categorie_personnel) REFERENCES categorie_personnel (id_categorie_personnel)
);

CREATE TABLE poste
(
    id_poste       SERIAL,
    nom            VARCHAR(50) NOT NULL,
    id_departement INTEGER     NOT NULL,
    PRIMARY KEY (id_poste),
    FOREIGN KEY (id_departement) REFERENCES departement (id_departement)
);

CREATE TABLE personnnel_contrat
(
    id_poste        INTEGER,
    id_personnel    INTEGER,
    date_debut      DATE           NOT NULL,
    date_fin        DATE,
    salaire         NUMERIC(15, 2) NOT NULL,
    id_type_contrat INTEGER        NOT NULL,
    PRIMARY KEY (id_poste, id_personnel),
    FOREIGN KEY (id_poste) REFERENCES poste (id_poste),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel),
    FOREIGN KEY (id_type_contrat) REFERENCES type_contrat (id_type_contrat)
);

CREATE TABLE solde_conge
(
    id_solde_conge SERIAL,
    annee          INTEGER        NOT NULL,
    conge_utilise  NUMERIC(15, 2) NOT NULL,
    id_personnel   INTEGER        NOT NULL,
    id_type_conge  INTEGER        NOT NULL,
    PRIMARY KEY (id_solde_conge),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel),
    FOREIGN KEY (id_type_conge) REFERENCES type_conge (id_type_conge)
);

CREATE TABLE demande
(
    id_demande   SERIAL,
    nature       VARCHAR(250) NOT NULL,
    debut        DATE,
    fin          DATE,
    etat         INTEGER      NOT NULL,
    date_demande DATE         NOT NULL,
    id_personnel INTEGER      NOT NULL,
    id_type_conge  INTEGER        NOT NULL,
    PRIMARY KEY (id_demande),
    FOREIGN KEY (id_type_conge) REFERENCES type_conge (id_type_conge),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel)
);

CREATE TABLE presence
(
    id_presence  SERIAL,
    date_        TIMESTAMP NOT NULL,
    etat         INTEGER   NOT NULL,
    id_personnel INTEGER   NOT NULL,
    PRIMARY KEY (id_presence),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel)
);

CREATE TABLE heure_sup
(
    id_heure_sup SERIAL,
    nb_heure     NUMERIC(15, 2) NOT NULL,
    pourcentage  NUMERIC(15, 2) NOT NULL,
    date_        DATE           NOT NULL,
    id_personnel INTEGER        NOT NULL,
    PRIMARY KEY (id_heure_sup),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel)
);

CREATE TABLE payement
(
    id_payement  SERIAL,
    montant      NUMERIC(15, 2) NOT NULL,
    date_        TIMESTAMP      NOT NULL,
    mois         INTEGER        NOT NULL,
    id_personnel INTEGER        NOT NULL,
    PRIMARY KEY (id_payement),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel)
);

CREATE TABLE document
(
    id_personnel     INTEGER,
    id_type_document INTEGER,
    docs             VARCHAR(150) NOT NULL,
    type_fichier     VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id_personnel, id_type_document),
    FOREIGN KEY (id_personnel) REFERENCES personnel (id_personnel),
    FOREIGN KEY (id_type_document) REFERENCES type_document (id_type_document)
);
