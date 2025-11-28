package com.rh.service;

import com.rh.repository.PrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimeService {

    @Autowired
    private PrimeRepository primeRepository;

    public double calculatePrimeForPersonnel(Long personnelId) {
        return 0.0;
    }
}
