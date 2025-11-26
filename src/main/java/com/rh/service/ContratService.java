package com.rh.service;

import com.rh.model.PersonnelContrat;
import com.rh.repository.PersonnelContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratService {

    @Autowired
    private PersonnelContratRepository contratRepository;

    public List<PersonnelContrat> findAll() {
        return contratRepository.findAll();
    }
}