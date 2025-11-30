package com.rh.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import java.time.Period;

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

    @Column(name = "matricule", nullable = false, length = 100)
    private String matricule;

    @Column(name = "lieu_naissance", length = 100)
    private String lieuNaissance;

    @Column(name = "nationalite", length = 50)
    private String nationalite = "MALAGASY";

    @Column(name = "adresse", length = 100)
    private String adresse;

    @Column(name = "stf", length = 50)
    private String stf;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", unique = true)
    @OneToOne(mappedBy = "personnel")
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "id_categorie_personnel", nullable = false)
    private CategoriePersonnel categoriePersonnel;

    @Column(name = "numero_cnaps",unique = true)
    private String numeroCnaps;

    public Personnel() {}
  

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

    public Personnel(LocalDate dtn, Integer statut, String nom, String prenom, String photo,
            String matricule, String lieuNaissance, String nationalite, String adresse, String stf,
            String telephone, Utilisateur utilisateur, Genre genre, CategoriePersonnel categoriePersonnel) {
        this.dtn = dtn;
        this.statut = statut;
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.matricule = matricule;
        this.lieuNaissance = lieuNaissance;
        this.nationalite = nationalite;
        this.adresse = adresse;
        this.stf = stf;
        this.telephone = telephone;
        this.utilisateur = utilisateur;
        this.genre = genre;
        this.categoriePersonnel = categoriePersonnel;
    }

    // --- GETTERS & SETTERS ---


    public String getNumeroCnaps() {
        return numeroCnaps;
    }

    public void setNumeroCnaps(String numeroCnaps) {
        this.numeroCnaps = numeroCnaps;
    }

    public Long getIdPersonnel() { return idPersonnel; }

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

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String phone) {
        this.telephone = phone;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStf() {
        return stf;
    }

    public void setStf(String stf) {
        this.stf = stf;
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

    public String getAnciennete() { return anciennete; }
    public void setAnciennete(String anciennete) { this.anciennete = anciennete; }

    public int getAge() {
        if (this.dtn == null) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        return Period.between(this.dtn, now).getYears();
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
                ", matricule='" + matricule + '\'' +
                ", lieuNaissance='" + lieuNaissance + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", adresse='" + adresse + '\'' +
                ", stf='" + stf + '\'' +
                ", utilisateur=" + utilisateur +
                ", genre=" + genre +
                ", categoriePersonnel=" + categoriePersonnel +
                '}';
    }
}
