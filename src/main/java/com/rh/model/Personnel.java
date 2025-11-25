package com.rh.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "personnel")
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personnel")
    private Long idPersonnel;

    @Column(name = "dtn", nullable = false)
    private LocalDate dtn;

    @Column(name = "statut", nullable = false)
    private Integer statut;

    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Column(name = "photo", nullable = false, length = 150)
    private String photo;

    @OneToOne(mappedBy = "personnel")
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_categorie_personnel", nullable = false)
    private CategoriePersonnel categoriePersonnel;

    public Personnel() {
    }

    public Personnel(LocalDate dtn, Integer statut, String nom, String prenom, String photo, Genre genre,
            CategoriePersonnel categoriePersonnel) {
        this.dtn = dtn;
        this.statut = statut;
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.genre = genre;
        this.categoriePersonnel = categoriePersonnel;
    }

    // Getters et setters...
    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public LocalDate getDtn() {
        return dtn;
    }

    public void setDtn(LocalDate dtn) {
        this.dtn = dtn;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public CategoriePersonnel getCategoriePersonnel() {
        return categoriePersonnel;
    }

    public void setCategoriePersonnel(CategoriePersonnel categoriePersonnel) {
        this.categoriePersonnel = categoriePersonnel;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "idPersonnel=" + idPersonnel +
                ", dtn=" + dtn +
                ", statut=" + statut +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", photo='" + photo + '\'' +
                ", genre=" + genre +
                ", categoriePersonnel=" + categoriePersonnel +
                '}';
    }
}