package com.rh.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "solde_conge")
public class SoldeConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solde_conge")
    private Long idSoldeConge;

    @Column(name = "annee", nullable = false)
    private Integer annee;

    @Column(name = "conge_utilise", nullable = false, precision = 15, scale = 2)
    private BigDecimal congeUtilise;

    @ManyToOne
    @JoinColumn(name = "id_personnel", nullable = false)
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "id_type_conge", nullable = false)
    private TypeConge typeConge;

    public SoldeConge() {
    }

    public SoldeConge(Integer annee, BigDecimal congeUtilise, Personnel personnel, TypeConge typeConge) {
        this.annee = annee;
        this.congeUtilise = congeUtilise;
        this.personnel = personnel;
        this.typeConge = typeConge;
    }

    public Long getIdSoldeConge() {
        return idSoldeConge;
    }

    public void setIdSoldeConge(Long idSoldeConge) {
        this.idSoldeConge = idSoldeConge;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public BigDecimal getCongeUtilise() {
        return congeUtilise;
    }

    public void setCongeUtilise(BigDecimal congeUtilise) {
        this.congeUtilise = congeUtilise;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public TypeConge getTypeConge() {
        return typeConge;
    }

    public void setTypeConge(TypeConge typeConge) {
        this.typeConge = typeConge;
    }

    @Override
    public String toString() {
        return "SoldeConge{" +
                "idSoldeConge=" + idSoldeConge +
                ", annee=" + annee +
                ", congeUtilise=" + congeUtilise +
                ", personnel=" + personnel +
                ", typeConge=" + typeConge +
                '}';
    }
}
