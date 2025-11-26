package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horaire_travail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HoraireTravail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horaire")
    private Integer idHoraire;
    
    @Column(nullable = false, length = 100)
    private String libelle;
    
    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;
    
    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;
    
    @Column(name = "duree_pause", nullable = false)
    private Integer dureePause;
    
    @Column(name = "heure_pause_debut")
    private LocalTime heurePauseDebut;
    
    @Column(name = "heure_pause_fin")
    private LocalTime heurePauseFin;
    
    @Column(name = "tolerance_retard")
    private Integer toleranceRetard = 15;
    
    @Column(length = 20)
    private String couleur = "#3B82F6";
    
    @Column(nullable = false)
    private Boolean actif = true;
    
    // Constructeurs
    public HoraireTravail() {}
    
    // Getters et Setters (identiques à avant)
    public Integer getIdHoraire() { return idHoraire; }
    public void setIdHoraire(Integer idHoraire) { this.idHoraire = idHoraire; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }
    public Integer getDureePause() { return dureePause; }
    public void setDureePause(Integer dureePause) { this.dureePause = dureePause; }
    public LocalTime getHeurePauseDebut() { return heurePauseDebut; }
    public void setHeurePauseDebut(LocalTime heurePauseDebut) { this.heurePauseDebut = heurePauseDebut; }
    public LocalTime getHeurePauseFin() { return heurePauseFin; }
    public void setHeurePauseFin(LocalTime heurePauseFin) { this.heurePauseFin = heurePauseFin; }
    public Integer getToleranceRetard() { return toleranceRetard; }
    public void setToleranceRetard(Integer toleranceRetard) { this.toleranceRetard = toleranceRetard; }
    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }
}