package com.rh.controller;

import com.rh.model.PresenceJournaliere;
import com.rh.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/presence")
@CrossOrigin(origins = "*")
public class PresenceController {
    
    @Autowired
    private PresenceService presenceService;
    
    @GetMapping("/jour/{idPersonnel}")
    public ResponseEntity<PresenceJournaliere> getPresenceJour(
            @PathVariable Integer idPersonnel,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        return ResponseEntity.ok(presenceService.getPresenceJour(idPersonnel, date));
    }
    
    @GetMapping("/periode/{idPersonnel}")
    public ResponseEntity<List<PresenceJournaliere>> getPresencesPeriode(
            @PathVariable Integer idPersonnel,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        
        return ResponseEntity.ok(presenceService.getPresencesPeriode(idPersonnel, debut, fin));
    }
    
    @GetMapping("/mois/{idPersonnel}")
    public ResponseEntity<List<PresenceJournaliere>> getPresencesMois(
            @PathVariable Integer idPersonnel,
            @RequestParam int mois,
            @RequestParam int annee) {
        
        return ResponseEntity.ok(presenceService.getPresencesMois(idPersonnel, mois, annee));
    }
}