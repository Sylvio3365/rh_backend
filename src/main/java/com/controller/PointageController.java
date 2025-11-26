package com.controller;

import com.model.HoraireTravail;
import com.model.Pointage;
import com.repository.HoraireTravailRepository;
import com.service.PointageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pointage")
@CrossOrigin(origins = "*")
public class PointageController {
    
    @Autowired
    private PointageService pointageService;
    
    @Autowired
    private HoraireTravailRepository horaireTravailRepository;
    
    /**
     * Enregistrer un pointage
     */
    @PostMapping("/enregistrer")
    public ResponseEntity<?> enregistrerPointage(@RequestBody Pointage pointage) {
        try {
            Pointage saved = pointageService.enregistrerPointage(pointage);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Erreur : " + e.getMessage()
            ));
        }
    }
    
    /**
     * Récupérer tous les horaires actifs
     */
    @GetMapping("/horaires")
    public ResponseEntity<List<HoraireTravail>> getHorairesActifs() {
        return ResponseEntity.ok(horaireTravailRepository.findByActifTrue());
    }
    
    /**
     * Récupérer les pointages d'une journée
     */
    @GetMapping("/jour/{idPersonnel}")
    public ResponseEntity<List<Pointage>> getPointagesJour(
            @PathVariable Integer idPersonnel,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        LocalDate dateRecherche = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(pointageService.getPointagesJour(idPersonnel, dateRecherche));
    }
    
    /**
     * Récupérer tous les pointages d'un personnel
     */
    @GetMapping("/personnel/{idPersonnel}")
    public ResponseEntity<List<Pointage>> getPointagesPersonnel(@PathVariable Integer idPersonnel) {
        return ResponseEntity.ok(pointageService.getPointagesPersonnel(idPersonnel));
    }
    
    /**
     * Suggérer le prochain type de pointage
     */
    @GetMapping("/prochain-type/{idPersonnel}")
    public ResponseEntity<Map<String, String>> suggererProchainType(@PathVariable Integer idPersonnel) {
        return ResponseEntity.ok(pointageService.suggererProchainType(idPersonnel));
    }
    
    /**
     * Consolider une journée manuellement
     */
    @PostMapping("/consolider/{idPersonnel}")
    public ResponseEntity<?> consoliderJournee(
            @PathVariable Integer idPersonnel,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            pointageService.consoliderJournee(idPersonnel, date);
            return ResponseEntity.ok(Map.of("message", "Consolidation effectuée"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}