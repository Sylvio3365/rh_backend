package com.rh.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "heure_sup")
public class HeureSup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_heure_sup")
    private Long idHeureSup;

    @Column(name = "nb_heure", nullable = false, precision = 15, scale = 2)
    private BigDecimal nbHeure;

    @Column(name = "pourcentage", nullable = false, precision = 15, scale = 2)
    private BigDecimal pourcentage;

    @Column(name = "date_", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "id_personnel", nullable = false)
    private Personnel personnel;

    public HeureSup() {
    }

    public HeureSup(BigDecimal nbHeure, BigDecimal pourcentage, LocalDate date, Personnel personnel) {
        this.nbHeure = nbHeure;
        this.pourcentage = pourcentage;
        this.date = date;
        this.personnel = personnel;
    }

    public Long getIdHeureSup() {
        return idHeureSup;
    }

    public void setIdHeureSup(Long idHeureSup) {
        this.idHeureSup = idHeureSup;
    }

    public BigDecimal getNbHeure() {
        return nbHeure;
    }

    public void setNbHeure(BigDecimal nbHeure) {
        this.nbHeure = nbHeure;
    }

    public BigDecimal getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(BigDecimal pourcentage) {
        this.pourcentage = pourcentage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "HeureSup{" +
                "idHeureSup=" + idHeureSup +
                ", nbHeure=" + nbHeure +
                ", pourcentage=" + pourcentage +
                ", date=" + date +
                ", personnel=" + personnel +
                '}';
    }
}
