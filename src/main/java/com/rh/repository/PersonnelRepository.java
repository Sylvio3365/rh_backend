package com.rh.repository;

import com.rh.model.Personnel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    @Query("SELECT COUNT(p) FROM Personnel p WHERE p.genre.libelle = :libelle")
    long countByGenreName(String libelle);

    @Query("SELECT tc.libelle, COUNT(pc) FROM PersonnelContrat pc " +
            "JOIN pc.typeContrat tc " +
            "GROUP BY tc.libelle")
    List<Object[]> countByTypeContrat();

    @Query("SELECT COUNT(pc) FROM PersonnelContrat pc")
    long countPersonnelWithContract();

    @Query("SELECT COUNT(pc) FROM PersonnelContrat pc WHERE pc.typeContrat.libelle = :typeContrat")
    long countByContractType(@Param("typeContrat") String typeContrat);
}
