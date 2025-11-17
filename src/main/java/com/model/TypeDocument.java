package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_document")
public class TypeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_document")
    private Long idTypeDocument;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    public TypeDocument() {
    }

    public TypeDocument(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdTypeDocument() {
        return idTypeDocument;
    }

    public void setIdTypeDocument(Long idTypeDocument) {
        this.idTypeDocument = idTypeDocument;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeDocument{" +
                "idTypeDocument=" + idTypeDocument +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
