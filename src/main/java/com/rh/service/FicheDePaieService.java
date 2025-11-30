package com.rh.service;

import com.rh.model.PersonnelDetail;
import com.rh.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FicheDePaieService {

    @Autowired
    private PersonnelService personnelService;

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

    public PersonnelDetail getPersonnelDetail(Long personnelId) {
        return personnelService.getPersonnelDetailById(personnelId);
    }

    public Double retenueSanitaire(Double salaire)throws Exception{
        if (salaire == null && salaire <= 0) {
            throw new Exception("Le salaire est null lors du calcul cnaps");
        }
        return salaire * 0.01;
    }









}
