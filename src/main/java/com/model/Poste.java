package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "poste")
public class Poste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poste")
    private Long idPoste;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_departement", nullable = false)
    private Departement departement;

    public Poste() {
    }

    public Poste(String nom, Departement departement) {
        this.nom = nom;
        this.departement = departement;
    }

    public Long getIdPoste() {
        return idPoste;
    }

    public void setIdPoste(Long idPoste) {
        this.idPoste = idPoste;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "Poste{" +
                "idPoste=" + idPoste +
                ", nom='" + nom + '\'' +
                ", departement=" + departement +
                '}';
    }
}
