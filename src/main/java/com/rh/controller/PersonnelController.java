package com.rh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rh.model.Personnel;
import com.rh.service.PersonnelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/personnels")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPersonnel() {
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

    @GetMapping("/count")
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
}
