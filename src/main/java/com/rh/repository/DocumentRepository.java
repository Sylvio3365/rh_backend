package com.rh.repository;

import com.rh.model.Document;
import com.rh.model.DocumentId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, DocumentId> {
    List<Document> findByIdIdPersonnel(Long idPersonnel);
}
