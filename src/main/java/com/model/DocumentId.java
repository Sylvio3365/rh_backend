package com.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DocumentId implements Serializable {

    @Column(name = "id_personnel")
    private Long idPersonnel;

    @Column(name = "id_type_document")
    private Long idTypeDocument;

    public DocumentId() {
    }

    public DocumentId(Long idPersonnel, Long idTypeDocument) {
        this.idPersonnel = idPersonnel;
        this.idTypeDocument = idTypeDocument;
    }

    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public Long getIdTypeDocument() {
        return idTypeDocument;
    }

    public void setIdTypeDocument(Long idTypeDocument) {
        this.idTypeDocument = idTypeDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentId that = (DocumentId) o;
        return Objects.equals(idPersonnel, that.idPersonnel) && Objects.equals(idTypeDocument, that.idTypeDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersonnel, idTypeDocument);
    }

    @Override
    public String toString() {
        return "DocumentId{" +
                "idPersonnel=" + idPersonnel +
                ", idTypeDocument=" + idTypeDocument +
                '}';
    }
}
