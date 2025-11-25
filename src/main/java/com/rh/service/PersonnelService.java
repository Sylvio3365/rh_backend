package com.rh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rh.model.Personnel;
import com.rh.repository.PersonnelRepository;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    public int getTotalPersonnelCount() {
        return (int) personnelRepository.count();
    }

    public List<Personnel> findAll() {
        return personnelRepository.findAll();
    }

    public double[] calculateStatisticsGenderDistribution() {
        long total = personnelRepository.count();
        long maleCount = personnelRepository.countByGenreName("Homme");
        long femaleCount = personnelRepository.countByGenreName("Femme");
        double maleP = total > 0 ? (maleCount * 100.0) / total : 0;
        double femaleP = total > 0 ? (femaleCount * 100.0) / total : 0;
        maleP = Math.round(maleP * 100.0) / 100.0;
        femaleP = Math.round(femaleP * 100.0) / 100.0;
        return new double[] { maleP, femaleP };
    }
}
