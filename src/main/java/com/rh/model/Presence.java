package com.rh.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "presence")
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presence")
    private Long idPresence;

    @Column(name = "date_", nullable = false)
    private LocalDateTime date;

    @Column(name = "etat", nullable = false)
    private Integer etat;

    @ManyToOne
    @JoinColumn(name = "id_personnel", nullable = false)
    private Personnel personnel;

    public Presence() {
    }

    public Presence(LocalDateTime date, Integer etat, Personnel personnel) {
        this.date = date;
        this.etat = etat;
        this.personnel = personnel;
    }

    public Long getIdPresence() {
        return idPresence;
    }

    public void setIdPresence(Long idPresence) {
        this.idPresence = idPresence;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "Presence{" +
                "idPresence=" + idPresence +
                ", date=" + date +
                ", etat=" + etat +
                ", personnel=" + personnel +
                '}';
    }
}
