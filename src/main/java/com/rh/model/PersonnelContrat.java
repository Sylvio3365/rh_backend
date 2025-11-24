package com.rh.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "personnnel_contrat")
public class PersonnelContrat {

    @EmbeddedId
    private PersonnelContratId id;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "salaire", nullable = false, precision = 15, scale = 2)
    private BigDecimal salaire;

    @ManyToOne
    @JoinColumn(name = "id_type_contrat", nullable = false)
    private TypeContrat typeContrat;

    public PersonnelContrat() {
    }

    public PersonnelContrat(PersonnelContratId id, LocalDate dateDebut, LocalDate dateFin, BigDecimal salaire, TypeContrat typeContrat) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.salaire = salaire;
        this.typeContrat = typeContrat;
    }

    public PersonnelContratId getId() {
        return id;
    }

    public void setId(PersonnelContratId id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    @Override
    public String toString() {
        return "PersonnelContrat{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", salaire=" + salaire +
                ", typeContrat=" + typeContrat +
                '}';
    }
}
