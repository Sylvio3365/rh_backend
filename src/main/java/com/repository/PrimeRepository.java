package com.repository;

import com.model.Prime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long> {
}
