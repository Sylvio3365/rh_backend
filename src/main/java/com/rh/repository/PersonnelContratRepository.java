package com.rh.repository;

import com.rh.model.PersonnelContrat;
import com.rh.model.PersonnelContratId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PersonnelContratRepository extends JpaRepository<PersonnelContrat, PersonnelContratId> {
    List<PersonnelContrat> findByIdIdPersonnel(Long idPersonnel);

    @Query("SELECT pc FROM PersonnelContrat pc " +
            "JOIN FETCH pc.poste " +
            "JOIN FETCH pc.typeContrat " +
            "WHERE pc.id.idPersonnel = :idPersonnel " +
            "ORDER BY pc.dateDebut DESC")
    List<PersonnelContrat> findCurrentContratByPersonnelId(@Param("idPersonnel") Long idPersonnel);
}
