package com.rh.service;

import com.rh.model.Personnel;
import com.rh.repository.PersonnelRepository;
import com.rh.model.PersonnelContrat;
import com.rh.repository.PersonnelContratRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;
    private final PersonnelContratRepository contratRepository;

    public PersonnelService(PersonnelRepository personnelRepository , PersonnelContratRepository contratRepository) {
        this.personnelRepository = personnelRepository;
        this.contratRepository = contratRepository;
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

}
