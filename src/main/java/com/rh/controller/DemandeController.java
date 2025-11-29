package com.rh.controller;

import com.rh.model.Demande;
import com.rh.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demandes")
// Pas de @CrossOrigin ici, utilisez votre config globale
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        try {
            Demande demande = demandeService.findById(id);
            if (demande != null) {
                return ResponseEntity.ok(demande);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE - Créer une demande de congé
    @PostMapping
    public ResponseEntity<Demande> createDemande(@RequestBody Demande demande) {
        try {
            // Définir la date de la demande automatiquement
            demande.setDateDemande(LocalDate.now());

            // Définir la nature automatiquement selon le type de congé
            if (demande.getTypeConge() != null) {
                switch (demande.getTypeConge().getIdTypeConge().intValue()) {
                    case 1:
                        demande.setNature("Congé maternité");
                        break;
                    case 2:
                        demande.setNature("Congé paternité");
                        break;
                    case 3:
                        demande.setNature("Congé annuel");
                        break;
                    case 4:
                        demande.setNature("Congé maladie");
                        break;
                    case 5:
                        demande.setNature("Congé formation");
                        break;
                    case 6:
                        demande.setNature("Congé exceptionnel");
                        break;
                    default:
                        demande.setNature("Autre");
                        break;
                }
            }

            Demande nouvelleDemande = demandeService.create(demande);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleDemande);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Liste de toutes les demandes
    @GetMapping
    public ResponseEntity<List<Demande>> getAllDemandes() {
        try {
            List<Demande> demandes = demandeService.findAll();
            return ResponseEntity.ok(demandes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Modifier la demande
    @PutMapping("/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        try {
            Demande demandeUpdate = demandeService.update(id, demande);
            return ResponseEntity.ok(demandeUpdate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE - Supprimer la demande
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDemande(@PathVariable Long id) {
        try {
            demandeService.delete(id);
            return ResponseEntity.ok("Demande supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Mettre à jour uniquement le statut
    @PutMapping("/{id}/statut")
    public ResponseEntity<Demande> updateStatut(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        try {
            Integer etat = body.get("etat");
            Demande demande = demandeService.findById(id);
            demande.setEtat(etat);
            Demande demandeUpdate = demandeService.update(id, demande);
            return ResponseEntity.ok(demandeUpdate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Mettre à jour uniquement les dates
    @PutMapping("/{id}/dates")
    public ResponseEntity<Demande> updateDates(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String debut = body.get("debut");
            String fin = body.get("fin");

            Demande demande = demandeService.findById(id);
            demande.setDebut(LocalDate.parse(debut));
            demande.setFin(LocalDate.parse(fin));

            Demande demandeUpdate = demandeService.update(id, demande);
            return ResponseEntity.ok(demandeUpdate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}