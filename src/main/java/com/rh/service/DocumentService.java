package com.rh.service;

import com.rh.model.Document;
import com.rh.model.DocumentId;
import com.rh.repository.DocumentRepository;
import com.rh.model.TypeDocument;
import com.rh.repository.TypeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository docRepository;

    @Autowired
    private TypeDocumentRepository typeRepository;

    public List<Document> findAll() {
        return docRepository.findAll();
    }

    public List<Document> findByPersonnel(Long id){
        return docRepository.findByIdIdPersonnel(id);
    }

    public List<TypeDocument> findAllTypes() {
        return typeRepository.findAll();
    }

    public TypeDocument getById(Long id){
        return typeRepository.findById(id).orElse(null);
    }

    public Document save(Document document){
        return docRepository.save(document);
    }

    public void deleteById(DocumentId docId) {
        docRepository.deleteById(docId);
    }
}