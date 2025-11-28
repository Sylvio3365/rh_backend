package com.rh.controller;

import com.rh.model.Personnel;
import com.rh.service.PersonnelService;
import com.rh.model.PersonnelContrat;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/personnels")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    public List<Personnel> getAllPersonnel() {
        return personnelService.findAll();
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> findOneByNameFirstname(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom) {

        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Personnel> personnel = personnelService.findOneByNameFirstname(nom, prenom);

            if (personnel.isPresent()) {
                response.put("success", true);
                response.put("data", personnel.get());
                response.put("error", null);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("data", null);
                response.put("error", "Personnel non trouvé");
                response.put("message", "Aucun personnel correspondant aux critères de recherche");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public Personnel getPersonnelById(@PathVariable Long id) {
        return personnelService.findById(id);
    }

    @GetMapping("/{id}/contrats")
    public List<PersonnelContrat> getContratById(@PathVariable Long id) {
        return personnelService.getContratByPersonnel(id);
    }

    @GetMapping("/{id}/contrat/actif")
    public PersonnelContrat getContratActifById(@PathVariable Long id) {
        return personnelService.getContratActifByPersonnel(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPersonnels() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personnel> personnel = personnelService.findAll();
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

    @GetMapping("/statistics/gender-distribution")
    public ResponseEntity<Map<String, Object>> getGenderDistributionStatistics() {
        Map<String, Object> response = new HashMap<>();
        try {
            double[] stats = personnelService.calculateStatisticsGenderDistribution();
            Map<String, Double> data = new HashMap<>();
            data.put("malePercentage", stats[0]);
            data.put("femalePercentage", stats[1]);
            response.put("success", true);
            response.put("data", data);
            response.put("error", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/statistics/age-distribution")
    public ResponseEntity<Map<String, Object>> getAgeDistributionStatistics() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> ageStats = personnelService.calculateStatisticsAgeDistribution();
            response.put("success", true);
            response.put("data", ageStats);
            response.put("error", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/statistics/count")
    public ResponseEntity<Map<String, Object>> getPersonnelCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            long count = personnelService.getTotalPersonnelCount();
            response.put("success", true);
            response.put("data", count);
            response.put("error", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/statistics/contract-distribution")
    public ResponseEntity<Map<String, Object>> getContractDistributionStatistics() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> stats = personnelService.calculateStatisticsContractDistribution();

            // Vérifier si le service a retourné une erreur
            if (stats.get("success").equals(true)) {
                response.put("success", true);
                response.put("data", stats.get("data"));
                response.put("error", null);
                response.put("message", stats.get("message"));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("data", null);
                response.put("error", stats.get("error"));
                response.put("message", stats.get("message"));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            response.put("message", "Error calculating contract distribution statistics");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
