package com.rh.controller;

import com.rh.model.SoldeConge;
import com.rh.service.SoldeCongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/solde-conges")
public class SoldeCongeController {

    @Autowired
    private SoldeCongeService soldeCongeService;

    // CREATE - Créer un solde
    @PostMapping
    public ResponseEntity<SoldeConge> createSolde(@RequestBody SoldeConge soldeConge) {
        try {
            SoldeConge nouveauSolde = soldeCongeService.create(soldeConge);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauSolde);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Liste de tous les soldes
    @GetMapping
    public ResponseEntity<List<SoldeConge>> getAllSoldes() {
        try {
            List<SoldeConge> soldes = soldeCongeService.findAll();
            return ResponseEntity.ok(soldes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Solde par ID
    @GetMapping("/{id}")
    public ResponseEntity<SoldeConge> getSoldeById(@PathVariable Long id) {
        try {
            SoldeConge solde = soldeCongeService.findById(id);
            return ResponseEntity.ok(solde);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // READ - Soldes d'un personnel
    @GetMapping("/personnel/{idPersonnel}")
    public ResponseEntity<List<SoldeConge>> getSoldesByPersonnel(@PathVariable Long idPersonnel) {
        try {
            List<SoldeConge> soldes = soldeCongeService.getSoldesByPersonnel(idPersonnel);
            return ResponseEntity.ok(soldes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Récapitulatif des soldes d'un personnel
    @GetMapping("/personnel/{idPersonnel}/recapitulatif")
    public ResponseEntity<Map<String, Object>> getRecapitulatif(@PathVariable Long idPersonnel) {
        try {
            Map<String, Object> recapitulatif = soldeCongeService.getRecapitulatifSoldes(idPersonnel);
            return ResponseEntity.ok(recapitulatif);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Initialiser les soldes pour un personnel
    @PostMapping("/personnel/{idPersonnel}/initialiser")
    public ResponseEntity<String> initialiserSoldes(@PathVariable Long idPersonnel) {
        try {
            soldeCongeService.initialiserSoldesPersonnel(idPersonnel);
            return ResponseEntity.ok("Soldes initialisés avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'initialisation des soldes");
        }
    }

    // PUT - Utiliser des jours de congé
    @PutMapping("/personnel/{idPersonnel}/type/{idTypeConge}/utiliser")
    public ResponseEntity<SoldeConge> utiliserConge(
            @PathVariable Long idPersonnel,
            @PathVariable Long idTypeConge,
            @RequestParam BigDecimal jours) {
        try {
            SoldeConge soldeUpdate = soldeCongeService.utiliserConge(idPersonnel, idTypeConge, jours);
            return ResponseEntity.ok(soldeUpdate);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Modifier un solde
    @PutMapping("/{id}")
    public ResponseEntity<SoldeConge> updateSolde(@PathVariable Long id, @RequestBody SoldeConge soldeConge) {
        try {
            SoldeConge soldeUpdate = soldeCongeService.update(id, soldeConge);
            return ResponseEntity.ok(soldeUpdate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE - Supprimer un solde
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSolde(@PathVariable Long id) {
        try {
            soldeCongeService.delete(id);
            return ResponseEntity.ok("Solde supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}