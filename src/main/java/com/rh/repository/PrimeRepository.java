package com.rh.repository;

import com.rh.model.Prime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long> {
}
