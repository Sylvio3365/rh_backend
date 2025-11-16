package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre", columnDefinition = "SERIAL")
    private Long idGenre;

    @Column(name = "libelle", nullable = false, unique = true, length = 50)
    private String libelle;

    public Genre() {
    }

    public Genre(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "idGenre=" + idGenre +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}