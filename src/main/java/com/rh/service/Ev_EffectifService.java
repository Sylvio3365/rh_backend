package com.rh.service;

import com.rh.model.Ev_Effectif;
import com.rh.repository.Ev_EffectifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Ev_EffectifService {

    @Autowired
    private Ev_EffectifRepository evEffectifRepository;

    public List<Ev_Effectif> getEffectifByMoisRangeAndAnnee(Integer moisDebut, Integer moisFin, Integer annee) {
        return evEffectifRepository.findByMoisRangeAndAnnee(moisDebut, moisFin, annee);
    }
}