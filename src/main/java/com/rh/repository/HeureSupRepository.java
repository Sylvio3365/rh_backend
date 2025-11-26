package com.rh.repository;

import com.rh.model.HeureSup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HeureSupRepository extends JpaRepository<HeureSup, Integer> {
    
    List<HeureSup> findByPersonnelIdPersonnelAndDateBetween(
        Integer idPersonnel, LocalDate debut, LocalDate fin);
    
    @Query("SELECT h FROM HeureSup h WHERE h.personnel.idPersonnel = :idPersonnel " +
           "AND MONTH(h.date) = :mois AND YEAR(h.date) = :annee")
    List<HeureSup> findByPersonnelAndMoisAnnee(
        @Param("idPersonnel") Integer idPersonnel,
        @Param("mois") int mois,
        @Param("annee") int annee);
    
    @Query("SELECT h FROM HeureSup h WHERE h.presenceJournaliere.idPresenceJour = :idPresenceJour")
    List<HeureSup> findByPresenceJournaliere(@Param("idPresenceJour") Integer idPresenceJour);
}