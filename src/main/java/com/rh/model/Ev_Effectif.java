package com.rh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ev_effectif")
public class Ev_Effectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nbemp", nullable = false)
    private Integer nbEmp;

    @Column(name = "annee", nullable = false)
    private Integer annee;

    @Column(name = "mois", nullable = false)
    private Integer mois;

    public Ev_Effectif() {
    }

    public Ev_Effectif(Integer nbEmp, Integer annee, Integer mois) {
        this.nbEmp = nbEmp;
        this.annee = annee;
        this.mois = mois;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbEmp() {
        return nbEmp;
    }

    public void setNbEmp(Integer nbEmp) {
        this.nbEmp = nbEmp;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    @Override
    public String toString() {
        return "Ev_Effectif{" +
                "id=" + id +
                ", nbEmp=" + nbEmp +
                ", annee=" + annee +
                ", mois=" + mois +
                '}';
    }
}