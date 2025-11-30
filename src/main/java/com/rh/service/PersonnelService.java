package com.rh.service;

import com.rh.model.Personnel;
import com.rh.model.PersonnelDetail;
import com.rh.repository.PersonnelRepository;
import com.rh.model.PersonnelContrat;
import com.rh.repository.PersonnelContratRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PersonnelService {
    
    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PersonnelContratRepository personnelContratRepository;

    @Autowired
    private PersonnelContratRepository contratRepository;

  
    public int getTotalPersonnelCount() {
        return (int) personnelRepository.count();
    }

    public List<Personnel> findAll() {
        return personnelRepository.findAll();
    }

    public Personnel findById(Long id) {
        return personnelRepository.findById(id).orElse(null);
    }

    public List<PersonnelContrat> getContratByPersonnel(Long idPersonnel){
        return contratRepository.findByIdIdPersonnel(idPersonnel);
    }

    public PersonnelContrat getContratActifByPersonnel(Long idPersonnel) {
        List<PersonnelContrat> contrats = contratRepository.findByIdIdPersonnel(idPersonnel);
        LocalDate now = LocalDate.now();
        for (PersonnelContrat pc : contrats) {
            boolean isActif = (pc.getDateFin() == null) || (!now.isBefore(pc.getDateDebut()) && !now.isAfter(pc.getDateFin()));
            if (isActif) {
                return pc;
            }
        }
        return null;
    }


    // public List<PersonnelDTO> getAllPersonnel() {
    //     List<PersonnelDTO> dto = new ArrayList<>();
    //     List<Personnel> personnels = personnelRepository.findAll();
    //     for(Personnel p : personnels){
    //         PersonnelDTO pd = new PersonnelDTO(p);
    //         dto.add(pd);
    //     }
    //     return dto;
    // }

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

    public Map<String, Object> calculateStatisticsAgeDistribution() {
        List<Personnel> personnels = personnelRepository.findAll();

        // Compteurs pour hommes
        int hommeInf25 = 0;
        int homme25et30 = 0;
        int homme31et40 = 0;
        int homme41et50 = 0;
        int hommeSup50 = 0;

        // Compteurs pour femmes
        int femmeInf25 = 0;
        int femme25et30 = 0;
        int femme31et40 = 0;
        int femme41et50 = 0;
        int femmeSup50 = 0;

        for (Personnel p : personnels) {
            int age = p.getAge();
            String genre = p.getGenre().getLibelle();

            if ("Homme".equalsIgnoreCase(genre)) {
                if (age < 25) {
                    hommeInf25++;
                } else if (age >= 25 && age <= 30) {
                    homme25et30++;
                } else if (age >= 31 && age <= 40) {
                    homme31et40++;
                } else if (age >= 41 && age <= 50) {
                    homme41et50++;
                } else {
                    hommeSup50++;
                }
            } else if ("Femme".equalsIgnoreCase(genre)) {
                if (age < 25) {
                    femmeInf25++;
                } else if (age >= 25 && age <= 30) {
                    femme25et30++;
                } else if (age >= 31 && age <= 40) {
                    femme31et40++;
                } else if (age >= 41 && age <= 50) {
                    femme41et50++;
                } else {
                    femmeSup50++;
                }
            }
        }

        Map<String, Object> ageChart = new HashMap<>();
        List<Map<String, Object>> series = new ArrayList<>();

        Map<String, Object> hommesSeries = new HashMap<>();
        hommesSeries.put("name", "Hommes");
        hommesSeries.put("data", List.of(hommeInf25, homme25et30, homme31et40, homme41et50, hommeSup50));

        Map<String, Object> femmesSeries = new HashMap<>();
        femmesSeries.put("name", "Femmes");
        femmesSeries.put("data", List.of(femmeInf25, femme25et30, femme31et40, femme41et50, femmeSup50));

        series.add(hommesSeries);
        series.add(femmesSeries);
        ageChart.put("series", series);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", ageChart);
        result.put("error", null);
        result.put("message", "Age distribution calculated successfully");
        return result;
    }

    public Map<String, Object> calculateStatisticsContractDistribution() {
        try {
            long total = personnelRepository.countPersonnelWithContract();

            long cdiCount = personnelRepository.countByContractType("CDI");
            long cddCount = personnelRepository.countByContractType("CDD");
            long essaiCount = personnelRepository.countByContractType("Contrat d essai");

            double cdiPercentage = total > 0 ? (cdiCount * 100.0) / total : 0;
            double cddPercentage = total > 0 ? (cddCount * 100.0) / total : 0;
            double essaiPercentage = total > 0 ? (essaiCount * 100.0) / total : 0;

            cdiPercentage = Math.round(cdiPercentage * 100.0) / 100.0;
            cddPercentage = Math.round(cddPercentage * 100.0) / 100.0;
            essaiPercentage = Math.round(essaiPercentage * 100.0) / 100.0;

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", Map.of(
                    "cdiPercentage", cdiPercentage,
                    "cddPercentage", cddPercentage,
                    "essaiPercentage", essaiPercentage,
                    "cdiCount", cdiCount,
                    "cddCount", cddCount,
                    "essaiCount", essaiCount,
                    "total", total));
            result.put("error", null);
            result.put("message", "Contract distribution calculated successfully");

            return result;

        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("data", null);
            result.put("error", e.getMessage());
            result.put("message", "Error calculating contract distribution");
            return result;
        }
    }

    public PersonnelDetail getPersonnelDetailById(Long idPersonnel) {
        // Récupérer le personnel
        Optional<Personnel> personnelOpt = personnelRepository.findById(idPersonnel);
        if (personnelOpt.isEmpty()) {
            throw new RuntimeException("Personnel non trouvé avec l'id: " + idPersonnel);
        }

        Personnel personnel = personnelOpt.get();

        List<PersonnelContrat> contrats = personnelContratRepository.findCurrentContratByPersonnelId(idPersonnel);

        if (contrats.isEmpty()) {
            return new PersonnelDetail(
                    personnel,
                    null, // pas de poste
                    null, // pas de type de contrat
                    null, // pas de salaire
                    null, // pas de date début
                    null  // pas de date fin
            );
        }

        // Prendre le contrat le plus récent (premier de la liste triée par date décroissante)
        PersonnelContrat currentContrat = contrats.get(0);

        // Construire et retourner le PersonnelDetail
        return new PersonnelDetail(
                personnel,
                currentContrat.getPoste(),
                currentContrat.getTypeContrat(),
                currentContrat.getSalaire(),
                currentContrat.getDateDebut(),
                currentContrat.getDateFin()
        );
    }

}
