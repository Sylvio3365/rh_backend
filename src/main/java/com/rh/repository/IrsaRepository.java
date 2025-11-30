package com.rh.repository;

import com.rh.model.Irsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IrsaRepository extends JpaRepository<Irsa, Long> {
    List<Irsa> findAllByOrderByMinAsc();
}
