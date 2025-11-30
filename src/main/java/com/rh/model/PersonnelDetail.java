package com.rh.model;

import com.rh.model.Personnel;
import com.rh.model.Poste;
import com.rh.model.TypeContrat;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonnelDetail {

    private Personnel personnel;
    private Poste poste;
    private TypeContrat typeContrat;
    private BigDecimal salaire;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public PersonnelDetail() {
    }

    public PersonnelDetail(Personnel personnel, Poste poste, TypeContrat typeContrat,
                           BigDecimal salaire, LocalDate dateDebut, LocalDate dateFin) {
        this.personnel = personnel;
        this.poste = poste;
        this.typeContrat = typeContrat;
        this.salaire = salaire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters et Setters
    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    // Méthodes utilitaires pour les salaires
    public BigDecimal getSalaireJournalier() throws Exception{
        if (this.salaire != null) {
            return this.salaire.divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP);
        }
        throw new Exception("Le salaire est null");
    }

    public BigDecimal getSalaireHoraire() throws Exception{
        if (this.salaire != null) {
            return this.salaire.divide(BigDecimal.valueOf(173), 2, BigDecimal.ROUND_HALF_UP);
        }
        throw new Exception("Le salaire est null");

    }



    @Override
    public String toString() {
        return "PersonnelDetail{" +
                "personnel=" + personnel +
                ", poste=" + poste +
                ", typeContrat=" + typeContrat +
                ", salaire=" + salaire +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
