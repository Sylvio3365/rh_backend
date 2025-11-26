package com.rh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rh.model.Utilisateur;
import com.rh.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur authentifier(String username, String pass) {
        Utilisateur u = utilisateurRepository.findByUsername(username);
        if (u != null && u.getPassword().equals(pass)) {
            return u;
        }
        return null;
    }
}
