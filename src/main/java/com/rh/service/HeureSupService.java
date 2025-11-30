package com.rh.service;

import com.rh.model.HeureSup;
import com.rh.model.Personnel;
import com.rh.model.PresenceJournaliere;
import com.rh.repository.HeureSupRepository;
import com.rh.repository.PersonnelRepository;
import com.rh.repository.PresenceJournaliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HeureSupService {
    
    @Autowired
    private HeureSupRepository heureSupRepository;
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private PresenceJournaliereRepository presenceJournaliereRepository;
    
    /**
     * MÉTHODE 1 : Insertion automatique (via procédure stockée)
     */
    @Transactional
    public HeureSup creerHeureSupAutomatique(Long idPersonnel, LocalDate date,
                                              BigDecimal nbHeure, BigDecimal pourcentage,
                                              Long idPresenceJour) {
        Personnel personnel = personnelRepository.findById(idPersonnel)
                .orElseThrow(() -> new RuntimeException("Personnel introuvable"));
        
        PresenceJournaliere presenceJournaliere = null;
        if (idPresenceJour != null) {
            presenceJournaliere = presenceJournaliereRepository.findById(idPresenceJour)
                    .orElse(null);
        }
        
        HeureSup heureSup = new HeureSup(personnel, date, nbHeure, pourcentage, presenceJournaliere);
        return heureSupRepository.save(heureSup);
    }
    
    /**
     * MÉTHODE 2 : Insertion manuelle (pour week-end, férié, nuit)
     */
    @Transactional
    public HeureSup creerHeureSupManuelle(HeureSup heureSup) {
        // Vérifier personnel existe
        Personnel personnel = personnelRepository.findById(heureSup.getPersonnel().getIdPersonnel())
                .orElseThrow(() -> new RuntimeException("Personnel introuvable"));
        
        // Vérifier pourcentage valide
        if (!isPourcentageValide(heureSup.getPourcentage())) {
            throw new RuntimeException("Pourcentage invalide. Valeurs: 30, 50, 100, 130");
        }
        
        heureSup.setPersonnel(personnel);
        heureSup.setPresenceJournaliere(null); // NULL pour cas spéciaux
        
        return heureSupRepository.save(heureSup);
    }
    
    /**
     * Vérifier si le pourcentage est valide
     */
    private boolean isPourcentageValide(BigDecimal pourcentage) {
        return pourcentage.compareTo(new BigDecimal("30")) == 0 ||
               pourcentage.compareTo(new BigDecimal("50")) == 0 ||
               pourcentage.compareTo(new BigDecimal("100")) == 0 ||
               pourcentage.compareTo(new BigDecimal("130")) == 0;
    }
    
    /**
     * Récupérer les heures sup d'une période
     */
    public List<HeureSup> getHeuresSupPeriode(Long idPersonnel, LocalDate debut, LocalDate fin) {
        return heureSupRepository.findByPersonnelIdPersonnelAndDateBetween(idPersonnel, debut, fin);
    }
    
    /**
     * Récupérer les heures sup d'un mois
     */
    public List<HeureSup> getHeuresSupMois(Long idPersonnel, int mois, int annee) {
        return heureSupRepository.findByPersonnelAndMoisAnnee(idPersonnel, mois, annee);
    }
    
    /**
     * Supprimer des heures sup
     */
    @Transactional
    public void supprimerHeureSup(Long idHeureSup) {
        heureSupRepository.deleteById(idHeureSup);
    }

    /**
     * Calculer les montants des heures supplémentaires par pourcentage
     * @param salaireHoraire Le salaire horaire de base
     * @return Map avec pourcentage en clé et montant horaire en valeur
     */
    public Map<Double, Double> calculerHeuresSupplementaires(double salaireHoraire) {
        Map<Double, Double> heuresSup = new HashMap<>();

        // Heures supplémentaires majorées de 30%
        heuresSup.put(30.0, salaireHoraire * 1.30);

        // Heures supplémentaires majorées de 40%
        heuresSup.put(40.0, salaireHoraire * 1.40);

        // Heures supplémentaires majorées de 50%
        heuresSup.put(50.0, salaireHoraire * 1.50);

        // Heures supplémentaires majorées de 100%
        heuresSup.put(100.0, salaireHoraire * 2.0);

        // Majoration pour heures de nuit (fixe ou calculée)
        // Si vous voulez un pourcentage, utilisez par exemple 25% pour la nuit
        heuresSup.put(130.0, salaireHoraire * 0.3);
        // Ou si c'est un montant fixe, vous pouvez l'ajouter séparément

        return heuresSup;
    }
}