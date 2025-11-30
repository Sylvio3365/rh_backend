package com.rh.controller;

import com.rh.model.Document;
import com.rh.model.DocumentId;
import com.rh.model.TypeDocument;
import com.rh.repository.TypeDocumentRepository;
import com.rh.service.DocumentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService docService;

    @Autowired
    private TypeDocumentRepository typeDocRepo;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDocuments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Document> personnel = docService.findAll();
            response.put("success", true);
            response.put("data", personnel);
            response.put("error", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    
    @GetMapping("/types")
    public ResponseEntity<Map<String, Object>> getTypeDocuments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<TypeDocument> personnel = docService.findAllTypes();
            response.put("success", true);
            response.put("data", personnel);
            response.put("error", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @RequestParam("fichier") MultipartFile file,
            @RequestParam("id_personnel") Long idPersonnel,
            @RequestParam("id_type_document") Long idType) {

        Map<String, Object> response = new HashMap<>();

        try {

            if (file.isEmpty()) {
                response.put("success", false);
                response.put("error", "Fichier vide");
                return ResponseEntity.badRequest().body(response);
            }

            String uploadDir = "uploads/documents/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            Files.write(path, file.getBytes());

            Document d = new Document();
            TypeDocument typeDoc = docService.getById(idType);
            DocumentId docId = new DocumentId(idPersonnel, idType);
            d.setId(docId);
            d.setDocs(fileName);
            d.setTypeDocument(typeDoc);
            d.setTypeFichier(file.getContentType());

            docService.save(d);

            response.put("success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{idPersonnel}/{idTypeDocument}")
    public ResponseEntity<Map<String, Object>> deleteDocument(
            @PathVariable Long idPersonnel,
            @PathVariable Long idTypeDocument
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            DocumentId docId = new DocumentId(idPersonnel, idTypeDocument);
            docService.deleteById(docId);

            response.put("success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        try {
            // chemin absolu du projet
            String basePath = new File("").getAbsolutePath();

            Path filePath = Paths.get(basePath + "/uploads/documents/" + filename);

            Resource resource = (Resource) new UrlResource(filePath.toUri());

            if (!((AbstractFileResolvingResource) resource).exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // LOG IMPORTANT
            return ResponseEntity.status(500).build();
        }
    }

}
