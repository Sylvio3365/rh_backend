package com.rh.controller;

import com.rh.model.Personnel;
import com.rh.service.PersonnelService;
import com.rh.model.PersonnelContrat;
import com.rh.repository.PersonnelContratRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personnels")
@CrossOrigin(origins = "*")
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping
    public List<Personnel> getAllPersonnel() {
        return personnelService.findAll();
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

    // @GetMapping
    // public List<PersonnelDTO> getAllPersonnel() {
    //     return personnelService.getAllPersonnel();
    // }

    // @GetMapping("/{id}")
    // public PersonnelDTO getPersonnelById(@PathVariable Long id) {
    //     return personnelService.getPersonnelById(id);
    // }
}
