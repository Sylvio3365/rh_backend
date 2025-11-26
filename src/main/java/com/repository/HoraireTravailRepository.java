package com.repository;

import com.model.HoraireTravail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoraireTravailRepository extends JpaRepository<HoraireTravail, Integer> {
    List<HoraireTravail> findByActifTrue();
}