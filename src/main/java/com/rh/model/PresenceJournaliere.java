package com.rh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "presence_journaliere")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PresenceJournaliere {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presence_jour")
    private Long idPresenceJour;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personnel", nullable = false)
    @JsonIgnoreProperties({"utilisateur", "genre", "categoriePersonnel"})
    private Personnel personnel;
    
    @Column(name = "date_", nullable = false)
    private LocalDate date;
    
    @Column(name = "heure_arrivee")
    private LocalTime heureArrivee;
    
    @Column(name = "heure_depart")
    private LocalTime heureDepart;
    
    @Column(name = "duree_pause_reel")
    private Integer dureePauseReel = 0;
    
    @Column(name = "heures_travaillees", precision = 5, scale = 2)
    private BigDecimal heuresTravaillees = BigDecimal.ZERO;
    
    @Column(name = "minutes_retard")
    private Integer minutesRetard = 0;
    
    @Column(name = "heures_supplementaires", precision = 5, scale = 2)
    private BigDecimal heuresSupplementaires = BigDecimal.ZERO;
    
    @Column(length = 20)
    private String statut = "EN_COURS";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_horaire")
    private HoraireTravail horaireTravail;
    
    @Column(columnDefinition = "TEXT")
    private String observation;
    
    // Constructeurs
    public PresenceJournaliere() {}
    
    // Getters et Setters
    public Long getIdPresenceJour() { return idPresenceJour; }
    public void setIdPresenceJour(Long idPresenceJour) { this.idPresenceJour = idPresenceJour; }
    public Personnel getPersonnel() { return personnel; }
    public void setPersonnel(Personnel personnel) { this.personnel = personnel; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(LocalTime heureArrivee) { this.heureArrivee = heureArrivee; }
    public LocalTime getHeureDepart() { return heureDepart; }
    public void setHeureDepart(LocalTime heureDepart) { this.heureDepart = heureDepart; }
    public Integer getDureePauseReel() { return dureePauseReel; }
    public void setDureePauseReel(Integer dureePauseReel) { this.dureePauseReel = dureePauseReel; }
    public BigDecimal getHeuresTravaillees() { return heuresTravaillees; }
    public void setHeuresTravaillees(BigDecimal heuresTravaillees) { this.heuresTravaillees = heuresTravaillees; }
    public Integer getMinutesRetard() { return minutesRetard; }
    public void setMinutesRetard(Integer minutesRetard) { this.minutesRetard = minutesRetard; }
    public BigDecimal getHeuresSupplementaires() { return heuresSupplementaires; }
    public void setHeuresSupplementaires(BigDecimal heuresSupplementaires) { this.heuresSupplementaires = heuresSupplementaires; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public HoraireTravail getHoraireTravail() { return horaireTravail; }
    public void setHoraireTravail(HoraireTravail horaireTravail) { this.horaireTravail = horaireTravail; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}