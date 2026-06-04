package com.jacob.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.inventario.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class ControllerCategorias {
    private final CategoriaService categoriaService;

    @Autowired
    public ControllerCategorias(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

   

}
