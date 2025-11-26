package com.rh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pointage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pointage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pointage")
    private Long idPointage;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personnel", nullable = false)
    @JsonIgnoreProperties({"utilisateur", "genre", "categoriePersonnel"})
    private Personnel personnel;
    
    @Column(name = "date_heure", nullable = false)
    private LocalDateTime dateHeure;
    
    @Column(name = "type_pointage", nullable = false, length = 20)
    private String typePointage;
    
    @Column(length = 20)
    private String methode = "QR_CODE";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_horaire")
    private HoraireTravail horaireTravail;
    
    @Column(length = 100)
    private String localisation;
    
    @Column(columnDefinition = "TEXT")
    private String remarque;
    
    // Constructeurs
    public Pointage() {}
    
    // Getters et Setters
    public Long getIdPointage() { return idPointage; }
    public void setIdPointage(Long idPointage) { this.idPointage = idPointage; }
    public Personnel getPersonnel() { return personnel; }
    public void setPersonnel(Personnel personnel) { this.personnel = personnel; }
    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }
    public String getTypePointage() { return typePointage; }
    public void setTypePointage(String typePointage) { this.typePointage = typePointage; }
    public String getMethode() { return methode; }
    public void setMethode(String methode) { this.methode = methode; }
    public HoraireTravail getHoraireTravail() { return horaireTravail; }
    public void setHoraireTravail(HoraireTravail horaireTravail) { this.horaireTravail = horaireTravail; }
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
    public String getRemarque() { return remarque; }
    public void setRemarque(String remarque) { this.remarque = remarque; }
}
