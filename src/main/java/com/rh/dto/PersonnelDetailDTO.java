package com.rh.dto;

public class PersonnelDetailDTO {

    private Long idPersonnel;
    private String nom;
    private String prenom;
    private String poste;
    private String typeContrat;
    private String dateDebutContrat;
    private String dateFinContrat;
    private Double salaire;

    public PersonnelDetailDTO() {
    }

    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getDateDebutContrat() {
        return dateDebutContrat;
    }

    public void setDateDebutContrat(String dateDebutContrat) {
        this.dateDebutContrat = dateDebutContrat;
    }

    public String getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(String dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }
}
