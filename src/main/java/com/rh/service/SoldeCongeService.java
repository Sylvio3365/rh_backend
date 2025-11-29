package com.rh.service;

import com.rh.model.Personnel;
import com.rh.model.SoldeConge;
import com.rh.model.TypeConge;
import com.rh.repository.PersonnelRepository;
import com.rh.repository.SoldeCongeRepository;
import com.rh.repository.TypeCongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SoldeCongeService {

    @Autowired
    private SoldeCongeRepository soldeCongeRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private TypeCongeRepository typeCongeRepository;

    // Créer un nouveau solde
    public SoldeConge create(SoldeConge soldeConge) {
        return soldeCongeRepository.save(soldeConge);
    }

    // Récupérer tous les soldes
    public List<SoldeConge> findAll() {
        return soldeCongeRepository.findAll();
    }

    // Récupérer un solde par ID
    public SoldeConge findById(Long id) {
        return soldeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solde de congé non trouvé avec l'ID : " + id));
    }
    /* */
    // Récupérer les soldes d'un personnel pour l'année en cours
    public List<SoldeConge> getSoldesByPersonnel(Long idPersonnel) {
        int anneeActuelle = Year.now().getValue();
        return soldeCongeRepository.findSoldesWithTypeConge(idPersonnel, anneeActuelle);
    }

    // Récupérer les soldes d'un personnel pour une année spécifique
    public List<SoldeConge> getSoldesByPersonnelAndAnnee(Long idPersonnel, Integer annee) {
        return soldeCongeRepository.findSoldesWithTypeConge(idPersonnel, annee);
    }

    // Récupérer le solde d'un personnel pour un type de congé spécifique
    public Optional<SoldeConge> getSoldeByPersonnelAndType(Long idPersonnel, Long idTypeConge) {
        int anneeActuelle = Year.now().getValue();
        return soldeCongeRepository.findByPersonnelIdPersonnelAndTypeCongeIdTypeCongeAndAnnee(
                idPersonnel, idTypeConge, anneeActuelle);
    }

    // Mettre à jour un solde
    public SoldeConge update(Long id, SoldeConge soldeConge) {
        SoldeConge existingSolde = findById(id);
        existingSolde.setAnnee(soldeConge.getAnnee());
        existingSolde.setCongeUtilise(soldeConge.getCongeUtilise());
        existingSolde.setPersonnel(soldeConge.getPersonnel());
        existingSolde.setTypeConge(soldeConge.getTypeConge());
        return soldeCongeRepository.save(existingSolde);
    }

    // Supprimer un solde
    public void delete(Long id) {
        soldeCongeRepository.deleteById(id);
    }

    // Initialiser les soldes pour un personnel (année en cours)
    public void initialiserSoldesPersonnel(Long idPersonnel) {
        Personnel personnel = personnelRepository.findById(idPersonnel)
                .orElseThrow(() -> new RuntimeException("Personnel non trouvé"));
        
        List<TypeConge> typesConge = typeCongeRepository.findAll();
        int anneeActuelle = Year.now().getValue();

        for (TypeConge typeConge : typesConge) {
            Optional<SoldeConge> existingSolde = soldeCongeRepository
                    .findByPersonnelIdPersonnelAndTypeCongeIdTypeCongeAndAnnee(
                            idPersonnel, typeConge.getIdTypeConge(), anneeActuelle);

            if (existingSolde.isEmpty()) {
                SoldeConge nouveauSolde = new SoldeConge();
                nouveauSolde.setPersonnel(personnel);
                nouveauSolde.setTypeConge(typeConge);
                nouveauSolde.setAnnee(anneeActuelle);
                nouveauSolde.setCongeUtilise(BigDecimal.ZERO);
                soldeCongeRepository.save(nouveauSolde);
            }
        }
    }

    // Utiliser des jours de congé
    public SoldeConge utiliserConge(Long idPersonnel, Long idTypeConge, BigDecimal joursUtilises) {
        int anneeActuelle = Year.now().getValue();
        
        SoldeConge solde = soldeCongeRepository
                .findByPersonnelIdPersonnelAndTypeCongeIdTypeCongeAndAnnee(
                        idPersonnel, idTypeConge, anneeActuelle)
                .orElseThrow(() -> new RuntimeException("Solde non trouvé pour ce personnel et ce type de congé"));

        BigDecimal nouveauSolde = solde.getCongeUtilise().add(joursUtilises);
        
        // Vérifier si le solde restant est suffisant
        if (nouveauSolde.compareTo(BigDecimal.valueOf(solde.getTypeConge().getNbMax())) > 0) {
            throw new RuntimeException("Solde de congés insuffisant");
        }

        solde.setCongeUtilise(nouveauSolde);
        return soldeCongeRepository.save(solde);
    }

    // Obtenir un récapitulatif des soldes avec calculs
    public Map<String, Object> getRecapitulatifSoldes(Long idPersonnel) {
        List<SoldeConge> soldes = getSoldesByPersonnel(idPersonnel);
        
        Map<String, Object> recapitulatif = new HashMap<>();
        
        for (SoldeConge solde : soldes) {
            Map<String, Object> detailsSolde = new HashMap<>();
            detailsSolde.put("type", solde.getTypeConge().getLibelle());
            detailsSolde.put("nbMax", solde.getTypeConge().getNbMax());
            detailsSolde.put("utilise", solde.getCongeUtilise());
            detailsSolde.put("restant", solde.getSoldeRestant());
            detailsSolde.put("annee", solde.getAnnee());
            
            recapitulatif.put(solde.getTypeConge().getLibelle(), detailsSolde);
        }
        
        return recapitulatif;
    }
}