package com.rh.controller;

import com.rh.model.Ev_Effectif;
import com.rh.service.Ev_EffectifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/effectif")
public class Ev_EffectifController {

    @Autowired
    private Ev_EffectifService evEffectifService;

    @GetMapping("/mois-range")
    public List<Ev_Effectif> getEffectifByMoisRange(
            @RequestParam Integer moisDebut,
            @RequestParam Integer moisFin,
            @RequestParam Integer annee) {
        return evEffectifService.getEffectifByMoisRangeAndAnnee(moisDebut, moisFin, annee);
    }
}