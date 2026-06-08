package com.jacob.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.inventario.entity.CategoriaEntity;
import com.jacob.inventario.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class ControllerCategorias {
    private final CategoriaService categoriaService;

    @Autowired
    public ControllerCategorias(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    @GetMapping("/findAll")
    public List<CategoriaEntity> findAll(){
        return this.categoriaService.findAll();
    }
   

}
