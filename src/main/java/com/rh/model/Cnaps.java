package com.rh.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cnaps")
public class Cnaps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cnaps")
    private Long idCnaps;

    @Column(name = "taux_entreprise", nullable = false, precision = 15, scale = 2)
    private BigDecimal tauxEntreprise;

    @Column(name = "taux_salarie", nullable = false, precision = 15, scale = 2)
    private BigDecimal tauxSalarie;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    public Cnaps() {
    }

    public Cnaps(BigDecimal tauxEntreprise, BigDecimal tauxSalarie, String libelle) {
        this.tauxEntreprise = tauxEntreprise;
        this.tauxSalarie = tauxSalarie;
        this.libelle = libelle;
    }

    public Long getIdCnaps() {
        return idCnaps;
    }

    public void setIdCnaps(Long idCnaps) {
        this.idCnaps = idCnaps;
    }

    public BigDecimal getTauxEntreprise() {
        return tauxEntreprise;
    }

    public void setTauxEntreprise(BigDecimal tauxEntreprise) {
        this.tauxEntreprise = tauxEntreprise;
    }

    public BigDecimal getTauxSalarie() {
        return tauxSalarie;
    }

    public void setTauxSalarie(BigDecimal tauxSalarie) {
        this.tauxSalarie = tauxSalarie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Cnaps{" +
                "idCnaps=" + idCnaps +
                ", tauxEntreprise=" + tauxEntreprise +
                ", tauxSalarie=" + tauxSalarie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
