package com.rh.repository;

import com.rh.model.Irsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrsaRepository extends JpaRepository<Irsa, Long> {
}
