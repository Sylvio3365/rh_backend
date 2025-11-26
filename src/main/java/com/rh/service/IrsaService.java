package com.rh.service;

import com.rh.dto.PersonnelDetailDTO;
import com.rh.repository.IrsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrsaService {

    @Autowired
    private IrsaRepository irsaRepository;

    public double calculateIrsaForPersonnel(PersonnelDetailDTO personnelDetailDTO) {

        return 0.0;
    }
}
