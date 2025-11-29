package com.rh.controller;

import com.rh.model.TypeConge;
import com.rh.service.TypeCongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type-conges")
public class TypeCongeController {

    @Autowired
    private TypeCongeService typeCongeService;

    @GetMapping
    public List<TypeConge> getAllTypeConge() {
        return typeCongeService.findAll();
    }
}
