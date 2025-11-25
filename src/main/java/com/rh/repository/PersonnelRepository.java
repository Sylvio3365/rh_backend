package com.rh.repository;

import com.rh.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    @Query("SELECT COUNT(p) FROM Personnel p WHERE p.genre.libelle = :libelle")
    long countByGenreName(String libelle);
}

