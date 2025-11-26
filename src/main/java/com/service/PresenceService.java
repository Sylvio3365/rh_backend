package com.service;

import com.model.PresenceJournaliere;
import com.repository.PresenceJournaliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PresenceService {
    
    @Autowired
    private PresenceJournaliereRepository presenceJournaliereRepository;
    
    public PresenceJournaliere getPresenceJour(Integer idPersonnel, LocalDate date) {
        return presenceJournaliereRepository.findByPersonnelIdPersonnelAndDate(idPersonnel, date)
                .orElse(null);
    }
    
    public List<PresenceJournaliere> getPresencesPeriode(Integer idPersonnel, 
                                                          LocalDate debut, LocalDate fin) {
        return presenceJournaliereRepository.findByPersonnelIdPersonnelAndDateBetween(
            idPersonnel, debut, fin);
    }
    
    public List<PresenceJournaliere> getPresencesMois(Integer idPersonnel, int mois, int annee) {
        return presenceJournaliereRepository.findByPersonnelAndMoisAnnee(idPersonnel, mois, annee);
    }
}