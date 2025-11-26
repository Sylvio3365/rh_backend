package com.rh.service;

import com.rh.dto.PersonnelDetailDTO;
import com.rh.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FicheDePaieService {


    @Autowired
    private CnapsService cnapsService;

    @Autowired
    private PrimeService primeService;

    @Autowired
    private IrsaService irsaService;

    public double calculateCnaps(Long personnelId) {
        return cnapsService.calculateCnapsForPersonnel(personnelId);
    }

    public double calculatePrime(Long personnelId) {
        return primeService.calculatePrimeForPersonnel(personnelId);
    }

}
