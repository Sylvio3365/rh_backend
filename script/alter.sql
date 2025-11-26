-- Ajouts des autres colonnes pour complters les informations
ALTER TABLE personnel ADD COLUMN matricule VARCHAR(100);
ALTER TABLE personnel ADD COLUMN lieu_naissance VARCHAR(100);
ALTER TABLE personnel ADD COLUMN nationalite VARCHAR(50) DEFAULT 'MALAGASY';
ALTER TABLE personnel ADD COLUMN adresse VARCHAR(100);
ALTER TABLE personnel ADD COLUMN stf VARCHAR(50);
ALTER TABLE personnel ADD COLUMN telephone VARCHAR(50);
