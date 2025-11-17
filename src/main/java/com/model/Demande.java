package com.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long idDemande;

    @Column(name = "nature", nullable = false, length = 250)
    private String nature;

    @Column(name = "debut")
    private LocalDate debut;

    @Column(name = "fin")
    private LocalDate fin;

    @Column(name = "etat", nullable = false)
    private Integer etat;

    @Column(name = "date_demande", nullable = false)
    private LocalDate dateDemande;

    @ManyToOne
    @JoinColumn(name = "id_personnel", nullable = false)
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "id_type_conge", nullable = false)
    private TypeConge typeConge;

    public Demande() {
    }

    public Demande(String nature, LocalDate debut, LocalDate fin, Integer etat, LocalDate dateDemande, Personnel personnel) {
        this.nature = nature;
        this.debut = debut;
        this.fin = fin;
        this.etat = etat;
        this.dateDemande = dateDemande;
        this.personnel = personnel;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "idDemande=" + idDemande +
                ", nature='" + nature + '\'' +
                ", debut=" + debut +
                ", fin=" + fin +
                ", etat=" + etat +
                ", dateDemande=" + dateDemande +
                ", personnel=" + personnel +
                '}';
    }
}
