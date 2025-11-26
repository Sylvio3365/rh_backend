package com.rh.repository;

import com.rh.model.Ev_Effectif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Ev_EffectifRepository extends JpaRepository<Ev_Effectif, Long> {

    @Query("SELECT e FROM Ev_Effectif e WHERE e.annee = :annee AND e.mois BETWEEN :moisDebut AND :moisFin")
    List<Ev_Effectif> findByMoisRangeAndAnnee(
            @Param("moisDebut") Integer moisDebut,
            @Param("moisFin") Integer moisFin,
            @Param("annee") Integer annee);
}