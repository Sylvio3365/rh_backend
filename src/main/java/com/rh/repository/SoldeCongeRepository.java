package com.rh.repository;

import com.rh.model.SoldeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoldeCongeRepository extends JpaRepository<SoldeConge, Long> {

    // Récupérer les soldes par personnel + année (avec type_conge fetché)
    @Query("""
            SELECT s FROM SoldeConge s
            JOIN FETCH s.typeConge
            WHERE s.personnel.idPersonnel = :idPersonnel
            AND s.annee = :annee
            """)
    List<SoldeConge> findSoldesWithTypeConge(Long idPersonnel, Integer annee);

    // Récupérer un solde spécifique (personnel + type_conge + année)
    Optional<SoldeConge> findByPersonnelIdPersonnelAndTypeCongeIdTypeCongeAndAnnee(
            Long idPersonnel,
            Long idTypeConge,
            Integer annee
    );
}
