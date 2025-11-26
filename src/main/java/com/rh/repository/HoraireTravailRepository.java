package com.rh.repository;

import com.rh.model.HoraireTravail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoraireTravailRepository extends JpaRepository<HoraireTravail, Long> {
    List<HoraireTravail> findByActifTrue();
}