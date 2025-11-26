package com.rh.service;

import com.rh.model.*;
import com.rh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PointageService {
    
    @Autowired
    private PointageRepository pointageRepository;
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private HoraireTravailRepository horaireTravailRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    /**
     * Enregistrer un pointage
     */
    @Transactional
    public Pointage enregistrerPointage(Pointage pointage) {
        // Vérifier personnel existe
        Personnel personnel = personnelRepository.findById(pointage.getPersonnel().getIdPersonnel())
                .orElseThrow(() -> new RuntimeException("Personnel introuvable"));
        
        // Vérifier horaire existe si fourni
        if (pointage.getHoraireTravail() != null && pointage.getHoraireTravail().getIdHoraire() != null) {
            HoraireTravail horaire = horaireTravailRepository.findById(
                pointage.getHoraireTravail().getIdHoraire())
                .orElseThrow(() -> new RuntimeException("Horaire introuvable"));
            pointage.setHoraireTravail(horaire);
        }
        
        pointage.setPersonnel(personnel);
        pointage.setDateHeure(LocalDateTime.now());
        pointage.setMethode("QR_CODE");
        
        Pointage saved = pointageRepository.save(pointage);
        
        // Si c'est une SORTIE, consolider la journée
        if ("SORTIE".equals(pointage.getTypePointage())) {
            consoliderJournee(personnel.getIdPersonnel(), LocalDate.now());
        }
        
        return saved;
    }
    
    /**
     * Consolider la journée (appel procédure stockée)
     */
    @Transactional
    public void consoliderJournee(Long idPersonnel, LocalDate date) {
        try {
            entityManager.createNativeQuery(
                "SELECT consolider_presence_jour(:idPersonnel, :date)")
                .setParameter("idPersonnel", idPersonnel)
                .setParameter("date", date)
                .getSingleResult();
        } catch (Exception e) {
            // Log l'erreur mais ne bloque pas le pointage
            System.err.println("Erreur lors de la consolidation: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Récupérer les pointages d'une journée
     */
    public List<Pointage> getPointagesJour(Integer idPersonnel, LocalDate date) {
        return pointageRepository.findByPersonnelAndDate(idPersonnel, date);
    }
    
    /**
     * Récupérer tous les pointages d'un personnel
     */
    public List<Pointage> getPointagesPersonnel(Integer idPersonnel) {
        return pointageRepository.findByPersonnelOrderByDateHeureDesc(idPersonnel);
    }
    
    /**
     * Suggérer le prochain type de pointage
     */
    public Map<String, String> suggererProchainType(Integer idPersonnel) {
        var dernierPointage = pointageRepository.findLastPointageByPersonnel(idPersonnel);
        
        String prochainType = "ENTREE";
        if (dernierPointage.isPresent()) {
            String dernierType = dernierPointage.get().getTypePointage();
            switch (dernierType) {
                case "ENTREE": prochainType = "PAUSE_DEBUT"; break;
                case "PAUSE_DEBUT": prochainType = "PAUSE_FIN"; break;
                case "PAUSE_FIN": prochainType = "SORTIE"; break;
                case "SORTIE": prochainType = "ENTREE"; break;
            }
        }
        
        return Map.of("prochainType", prochainType);
    }
}