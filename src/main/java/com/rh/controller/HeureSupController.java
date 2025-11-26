package com.rh.controller;

import com.rh.model.HeureSup;
import com.rh.service.HeureSupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/heure-sup")
@CrossOrigin(origins = "*")
public class HeureSupController {
    
    @Autowired
    private HeureSupService heureSupService;
    
    /**
     * INSERTION MANUELLE : Créer heures sup pour cas spéciaux
     */
    @PostMapping("/manuel")
    public ResponseEntity<?> creerHeureSupManuelle(@RequestBody HeureSup heureSup) {
        try {
            HeureSup saved = heureSupService.creerHeureSupManuelle(heureSup);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Récupérer les heures sup d'une période
     */
    @GetMapping("/periode/{idPersonnel}")
    public ResponseEntity<List<HeureSup>> getHeuresSupPeriode(
            @PathVariable Long idPersonnel,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        
        return ResponseEntity.ok(heureSupService.getHeuresSupPeriode(idPersonnel, debut, fin));
    }
    
    /**
     * Récupérer les heures sup d'un mois
     */
    @GetMapping("/mois/{idPersonnel}")
    public ResponseEntity<List<HeureSup>> getHeuresSupMois(
            @PathVariable Long idPersonnel,
            @RequestParam int mois,
            @RequestParam int annee) {
        
        return ResponseEntity.ok(heureSupService.getHeuresSupMois(idPersonnel, mois, annee));
    }
    
    /**
     * Supprimer des heures sup
     */
    @DeleteMapping("/{idHeureSup}")
    public ResponseEntity<?> supprimerHeureSup(@PathVariable Long idHeureSup) {
        try {
            heureSupService.supprimerHeureSup(idHeureSup);
            return ResponseEntity.ok(Map.of("message", "Heures sup supprimées"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}