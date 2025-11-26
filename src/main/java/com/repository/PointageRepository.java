package com.repository;

import com.model.Pointage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PointageRepository extends JpaRepository<Pointage, Integer> {
    
    @Query("SELECT p FROM Pointage p WHERE p.personnel.idPersonnel = :idPersonnel " +
           "AND DATE(p.dateHeure) = :date ORDER BY p.dateHeure DESC")
    List<Pointage> findByPersonnelAndDate(@Param("idPersonnel") Integer idPersonnel, 
                                          @Param("date") LocalDate date);
    
    @Query("SELECT p FROM Pointage p WHERE p.personnel.idPersonnel = :idPersonnel " +
           "ORDER BY p.dateHeure DESC")
    List<Pointage> findByPersonnelOrderByDateHeureDesc(@Param("idPersonnel") Integer idPersonnel);
    
    @Query(value = "SELECT * FROM pointage WHERE id_personnel = :idPersonnel " +
                   "ORDER BY date_heure DESC LIMIT 1", nativeQuery = true)
    Optional<Pointage> findLastPointageByPersonnel(@Param("idPersonnel") Integer idPersonnel);
}