package com.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payement")
public class Payement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payement")
    private Long idPayement;

    @Column(name = "montant", nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_", nullable = false)
    private LocalDateTime date;

    @Column(name = "mois", nullable = false)
    private Integer mois;

    @ManyToOne
    @JoinColumn(name = "id_personnel", nullable = false)
    private Personnel personnel;

    public Payement() {
    }

    public Payement(BigDecimal montant, LocalDateTime date, Integer mois, Personnel personnel) {
        this.montant = montant;
        this.date = date;
        this.mois = mois;
        this.personnel = personnel;
    }

    public Long getIdPayement() {
        return idPayement;
    }

    public void setIdPayement(Long idPayement) {
        this.idPayement = idPayement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "Payement{" +
                "idPayement=" + idPayement +
                ", montant=" + montant +
                ", date=" + date +
                ", mois=" + mois +
                ", personnel=" + personnel +
                '}';
    }
}
