package com.rh.repository;

import com.rh.model.CategoriePersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriePersonnelRepository extends JpaRepository<CategoriePersonnel, Long> {
}
