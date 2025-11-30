package com.rh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "heure_sup")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HeureSup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_heure_sup")
    private Long idHeureSup;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personnel", nullable = false)
    @JsonIgnoreProperties({"utilisateur", "genre", "categoriePersonnel"})
    private Personnel personnel;
    
    @Column(name = "date_", nullable = false)
    private LocalDate date;
    
    @Column(name = "nb_heure", nullable = false, precision = 5, scale = 2)
    private BigDecimal nbHeure;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal pourcentage;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_presence_jour")
    @JsonIgnoreProperties({"personnel", "horaireTravail"})
    private PresenceJournaliere presenceJournaliere;
    
    // Constructeurs
    public HeureSup() {}
    
    public HeureSup(Personnel personnel, LocalDate date, BigDecimal nbHeure, 
                    BigDecimal pourcentage, PresenceJournaliere presenceJournaliere) {
        this.personnel = personnel;
        this.date = date;
        this.nbHeure = nbHeure;
        this.pourcentage = pourcentage;
        this.presenceJournaliere = presenceJournaliere;
    }
    
    // Getters et Setters
    public Long getIdHeureSup() { return idHeureSup; }
    public void setIdHeureSup(Long idHeureSup) { this.idHeureSup = idHeureSup; }
    public Personnel getPersonnel() { return personnel; }
    public void setPersonnel(Personnel personnel) { this.personnel = personnel; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public BigDecimal getNbHeure() { return nbHeure; }
    public void setNbHeure(BigDecimal nbHeure) { this.nbHeure = nbHeure; }
    public BigDecimal getPourcentage() { return pourcentage; }
    public void setPourcentage(BigDecimal pourcentage) { this.pourcentage = pourcentage; }
    public PresenceJournaliere getPresenceJournaliere() { return presenceJournaliere; }
    public void setPresenceJournaliere(PresenceJournaliere presenceJournaliere) { this.presenceJournaliere = presenceJournaliere; }
}
