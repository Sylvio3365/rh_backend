package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "document")
public class Document {

    @EmbeddedId
    private DocumentId id;

    @Column(name = "docs", nullable = false, length = 150)
    private String docs;

    @Column(name = "type_fichier", nullable = false, length = 50)
    private String typeFichier;

    public Document() {
    }

    public Document(DocumentId id, String docs, String typeFichier) {
        this.id = id;
        this.docs = docs;
        this.typeFichier = typeFichier;
    }

    public DocumentId getId() {
        return id;
    }

    public void setId(DocumentId id) {
        this.id = id;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", docs='" + docs + '\'' +
                ", typeFichier='" + typeFichier + '\'' +
                '}';
    }
}
