CREATE OR REPLACE VIEW personnel_detail AS
SELECT 
    p.nom as Nom,
    p.prenom as Prenom,
    post.nom as poste,
    tc.*,
    pc.date_debut,
    pc.date_fin,
    pc.salaire
FROM personnel p
JOIN personnnel_contrat pc ON p.id_personnel = pc.id_personnel
JOIN poste post ON post.id_poste = pc.id_poste
JOIN type_contrat tc ON pc.id_type_contrat = tc.id_type_contrat
WHERE pc.date_debut = (
    SELECT MAX(pc2.date_debut)
    FROM personnnel_contrat pc2
    WHERE pc2.id_personnel = p.id_personnel
);

drop view if exists personnel_detail;
