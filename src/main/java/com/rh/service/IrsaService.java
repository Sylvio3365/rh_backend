package com.rh.service;

import com.rh.model.Irsa;
import com.rh.repository.IrsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class IrsaService {

    @Autowired
    private IrsaRepository irsaRepository;
    public List<Irsa> calculateIrsaForPersonnel(Double salaire) {
        List<Irsa> result = new ArrayList<>();

        if (salaire == null || salaire <= 0) {
            return result;
        }


        List<Irsa> tranchesIrsa = irsaRepository.findAllByOrderByMinAsc();

        BigDecimal salaireRestant = BigDecimal.valueOf(salaire);

        for (Irsa tranche : tranchesIrsa) {
            if (salaireRestant.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            // Créer une copie de la tranche pour le résultat
            Irsa trancheResult = new Irsa();
            trancheResult.setIdIrsa(tranche.getIdIrsa());
            trancheResult.setMin(tranche.getMin());
            trancheResult.setMax(tranche.getMax());
            trancheResult.setTaux(tranche.getTaux());

            BigDecimal min = tranche.getMin();
            BigDecimal max = tranche.getMax();
            BigDecimal taux = tranche.getTaux();

            // Calculer la base imposable pour cette tranche
            BigDecimal baseImposable;
            if (max == null) {
                // Dernière tranche (pas de max)
                baseImposable = salaireRestant;
            } else {
                BigDecimal plafondTranche = max.subtract(min);
                if (salaireRestant.compareTo(plafondTranche) > 0) {
                    baseImposable = plafondTranche;
                } else {
                    baseImposable = salaireRestant;
                }
            }

            // Vérifier si on est dans la tranche (salaire > min)
            if (salaireRestant.compareTo(min) > 0 && baseImposable.compareTo(BigDecimal.ZERO) > 0) {
                // Calculer le montant d'IRSA pour cette tranche
                BigDecimal montant = baseImposable.multiply(taux)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                trancheResult.setMontant(montant.doubleValue());
                result.add(trancheResult);

                // Mettre à jour le salaire restant
                salaireRestant = salaireRestant.subtract(baseImposable);
            } else {
                // Tranche non utilisée, montant à 0
                trancheResult.setMontant(0.0);
                result.add(trancheResult);
            }
        }

        return result;
    }

    // Méthode utilitaire pour obtenir le total IRSA
    public double getTotalIrsa(List<Irsa> irsaList) {
        return irsaList.stream()
                .mapToDouble(Irsa::getMontant)
                .sum();
    }

}
