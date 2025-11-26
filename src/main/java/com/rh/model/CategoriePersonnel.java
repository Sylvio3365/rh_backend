package com.rh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_personnel")
public class CategoriePersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie_personnel")
    private Long idCategoriePersonnel;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    public CategoriePersonnel() {
    }

    public CategoriePersonnel(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdCategoriePersonnel() {
        return idCategoriePersonnel;
    }

    public void setIdCategoriePersonnel(Long idCategoriePersonnel) {
        this.idCategoriePersonnel = idCategoriePersonnel;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "CategoriePersonnel{" +
                "idCategoriePersonnel=" + idCategoriePersonnel +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
