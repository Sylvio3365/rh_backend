package com.rh.repository;

import com.rh.model.TypeContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeContratRepository extends JpaRepository<TypeContrat, Long> {
}
