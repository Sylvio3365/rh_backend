package com.rh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_conge")
public class TypeConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_conge")
    private Long idTypeConge;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "nb_max")
    private Integer nbMax;

    public TypeConge() {
    }

    public TypeConge(String libelle, Integer nbMax) {
        this.libelle = libelle;
        this.nbMax = nbMax;
    }

    public Long getIdTypeConge() {
        return idTypeConge;
    }

    public void setIdTypeConge(Long idTypeConge) {
        this.idTypeConge = idTypeConge;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNbMax() {
        return nbMax;
    }

    public void setNbMax(Integer nbMax) {
        this.nbMax = nbMax;
    }

    @Override
    public String toString() {
        return "TypeConge{" +
                "idTypeConge=" + idTypeConge +
                ", libelle='" + libelle + '\'' +
                ", nbMax=" + nbMax +
                '}';
    }
}
