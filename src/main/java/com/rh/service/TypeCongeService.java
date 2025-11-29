package com.rh.service;

import com.rh.model.TypeConge;
import com.rh.repository.TypeCongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeCongeService {

    @Autowired
    private TypeCongeRepository typeCongeRepository;

    public List<TypeConge> findAll() {
        return typeCongeRepository.findAll();
    }

    public TypeConge findById(Long id) {
        return typeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Type de congé introuvable, id = " + id));
    }

    public TypeConge save(TypeConge typeConge) {
        return typeCongeRepository.save(typeConge);
    }
}
