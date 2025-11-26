package com.rh.controller;

import com.rh.model.PersonnelContrat;
import com.rh.service.ContratService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@CrossOrigin(origins = "*")
public class ContratController {

    private final ContratService contratService;

    public ContratController(ContratService contratService) {
        this.contratService = contratService;
    }

    @GetMapping
    public List<PersonnelContrat> getAll() {
        return contratService.findAll();
    }
}
