package com.repository;

import com.model.SoldeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldeCongeRepository extends JpaRepository<SoldeConge, Long> {
}
