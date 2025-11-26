package com.rh.repository;

import com.rh.model.SoldeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldeCongeRepository extends JpaRepository<SoldeConge, Long> {
}
