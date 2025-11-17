package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_contrat")
public class TypeContrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_contrat")
    private Long idTypeContrat;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    @Column(name = "nb_mois", nullable = false)
    private Integer nbMois;

    @Column(name = "renouvellement", nullable = false)
    private Integer renouvellement;

    public TypeContrat() {
    }

    public TypeContrat(String libelle, Integer nbMois, Integer renouvellement) {
        this.libelle = libelle;
        this.nbMois = nbMois;
        this.renouvellement = renouvellement;
    }

    public Long getIdTypeContrat() {
        return idTypeContrat;
    }

    public void setIdTypeContrat(Long idTypeContrat) {
        this.idTypeContrat = idTypeContrat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNbMois() {
        return nbMois;
    }

    public void setNbMois(Integer nbMois) {
        this.nbMois = nbMois;
    }

    public Integer getRenouvellement() {
        return renouvellement;
    }

    public void setRenouvellement(Integer renouvellement) {
        this.renouvellement = renouvellement;
    }

    @Override
    public String toString() {
        return "TypeContrat{" +
                "idTypeContrat=" + idTypeContrat +
                ", libelle='" + libelle + '\'' +
                ", nbMois=" + nbMois +
                ", renouvellement=" + renouvellement +
                '}';
    }
}
