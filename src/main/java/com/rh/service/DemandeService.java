package com.rh.service;

import com.rh.model.Demande;
import com.rh.repository.DemandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DemandeService {

    private final DemandeRepository demandeRepository;

    public DemandeService(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    // 🔹 Ajouter une demande
    public Demande create(Demande demande) {
        return demandeRepository.save(demande);
    }

    public Demande findById(Long id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable avec l'id : " + id));
    }

    // 🔹 Mettre à jour une demande
    public Demande update(Long id, Demande updated) {
        return demandeRepository.findById(id)
                .map(existing -> {
                    existing.setNature(updated.getNature());
                    existing.setDebut(updated.getDebut());
                    existing.setFin(updated.getFin());
                    existing.setEtat(updated.getEtat());
                    existing.setDateDemande(updated.getDateDemande());
                    existing.setPersonnel(updated.getPersonnel());
                    existing.setTypeConge(updated.getTypeConge());
                    return demandeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'id : " + id));
    }

    // 🔹 Supprimer
    public void delete(Long id) {
        demandeRepository.deleteById(id);
    }

    // 🔹 Obtenir toutes les demandes
    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

}
