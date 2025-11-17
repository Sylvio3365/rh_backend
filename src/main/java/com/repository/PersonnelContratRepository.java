package com.repository;

import com.model.PersonnelContrat;
import com.model.PersonnelContratId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelContratRepository extends JpaRepository<PersonnelContrat, PersonnelContratId> {
}
