package com.jacob.inventario.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.inventario.entity.ActivoTecnologicoEntity;
import com.jacob.inventario.service.ActivoTecnologicoService;

@RestController
@RequestMapping("/api")
public class Controller {
    private final ActivoTecnologicoService activoTecnologicoService;
    @Autowired
    public Controller(ActivoTecnologicoService activoTecnologicoService){
        this.activoTecnologicoService = activoTecnologicoService;
    }

    @PreAuthorize("hasRole('client')")
    @GetMapping("/liveness")
    public String liveness(){
        return "ok";
    }
    
}
