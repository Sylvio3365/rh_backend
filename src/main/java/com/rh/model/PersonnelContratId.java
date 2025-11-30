package com.rh.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PersonnelContratId implements Serializable {

    @Column(name = "id_poste")
    private Long idPoste;

    @Column(name = "id_personnel")
    @MapsId("idPersonnel")
    @JoinColumn(name = "id_personnel")
    private Long idPersonnel;

    public PersonnelContratId() {
    }

    public PersonnelContratId(Long idPoste, Long idPersonnel) {
        this.idPoste = idPoste;
        this.idPersonnel = idPersonnel;
    }

    public Long getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(Long idPoste) {
        this.idPoste = idPoste;
    }

    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonnelContratId that = (PersonnelContratId) o;
        return Objects.equals(idPoste, that.idPoste) && Objects.equals(idPersonnel, that.idPersonnel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPoste, idPersonnel);
    }

    @Override
    public String toString() {
        return "PersonnelContratId{" +
                "idPoste=" + idPoste +
                ", idPersonnel=" + idPersonnel +
                '}';
    }
}
