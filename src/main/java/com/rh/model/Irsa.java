package com.rh.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Irsa")
public class Irsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_irsa")
    private Long idIrsa;

    @Column(name = "min_", nullable = false, precision = 15, scale = 2)
    private BigDecimal min;

    @Column(name = "max_", precision = 15, scale = 2)
    private BigDecimal max;

    @Column(name = "taux", nullable = false, precision = 15, scale = 2)
    private BigDecimal taux;

    private double montant;

    public Irsa() {
    }

    public Irsa(BigDecimal min, BigDecimal max, BigDecimal taux) {
        this.min = min;
        this.max = max;
        this.taux = taux;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Long getIdIrsa() {
        return idIrsa;
    }

    public void setIdIrsa(Long idIrsa) {
        this.idIrsa = idIrsa;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    @Override
    public String toString() {
        return "Irsa{" +
                "idIrsa=" + idIrsa +
                ", min=" + min +
                ", max=" + max +
                ", taux=" + taux +
                '}';
    }
}
