package com.repository;

import com.model.PresenceJournaliere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PresenceJournaliereRepository extends JpaRepository<PresenceJournaliere, Integer> {
    
    Optional<PresenceJournaliere> findByPersonnelIdPersonnelAndDate(Integer idPersonnel, LocalDate date);
    
    List<PresenceJournaliere> findByPersonnelIdPersonnelAndDateBetween(
        Integer idPersonnel, LocalDate debut, LocalDate fin);
    
    @Query("SELECT p FROM PresenceJournaliere p WHERE p.personnel.idPersonnel = :idPersonnel " +
           "AND MONTH(p.date) = :mois AND YEAR(p.date) = :annee")
    List<PresenceJournaliere> findByPersonnelAndMoisAnnee(
        @Param("idPersonnel") Integer idPersonnel,
        @Param("mois") int mois,
        @Param("annee") int annee);
}
