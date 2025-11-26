package com.rh.repository;

import com.rh.model.PersonnelContrat;
import com.rh.model.PersonnelContratId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnelContratRepository extends JpaRepository<PersonnelContrat, PersonnelContratId> {
    List<PersonnelContrat> findByIdIdPersonnel(Long idPersonnel);
}
