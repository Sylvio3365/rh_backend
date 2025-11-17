package com.repository;

import com.model.HeureSup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeureSupRepository extends JpaRepository<HeureSup, Long> {
}
