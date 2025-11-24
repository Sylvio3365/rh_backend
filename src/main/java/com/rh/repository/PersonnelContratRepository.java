package com.rh.repository;

import com.rh.model.PersonnelContrat;
import com.rh.model.PersonnelContratId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelContratRepository extends JpaRepository<PersonnelContrat, PersonnelContratId> {
}
