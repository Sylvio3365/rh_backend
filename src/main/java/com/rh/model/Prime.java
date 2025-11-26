package com.rh.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "prime")
public class Prime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prime")
    private Long idPrime;

    @Column(name = "taux", nullable = false, precision = 15, scale = 2)
    private BigDecimal taux;

    @Column(name = "nb_annee", nullable = false)
    private Integer nbAnnee;

    public Prime() {
    }

    public Prime(BigDecimal taux, Integer nbAnnee) {
        this.taux = taux;
        this.nbAnnee = nbAnnee;
    }

    public Long getIdPrime() {
        return idPrime;
    }

    public void setIdPrime(Long idPrime) {
        this.idPrime = idPrime;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public Integer getNbAnnee() {
        return nbAnnee;
    }

    public void setNbAnnee(Integer nbAnnee) {
        this.nbAnnee = nbAnnee;
    }

    @Override
    public String toString() {
        return "Prime{" +
                "idPrime=" + idPrime +
                ", taux=" + taux +
                ", nbAnnee=" + nbAnnee +
                '}';
    }
}
