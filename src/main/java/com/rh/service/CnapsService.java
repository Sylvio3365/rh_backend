package com.rh.service;

import com.rh.repository.CnapsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CnapsService {

    @Autowired
    private CnapsRepository cnapsRepository;

    public double calculateCnapsForPersonnel(Long personnelId) {
        return 0.0;
    }

    public double calculateCnaps(Double salaire)throws Exception{
        if (salaire == null && salaire <= 0) {
            throw new Exception("Le salaire est null lors du calcul cnaps");
        }
        Double plafond = 350000*8*0.01;
        Double salaire1 = salaire*0.01;
        if(salaire1 < plafond){
            return salaire1;
        }
        return plafond;
    }
}
