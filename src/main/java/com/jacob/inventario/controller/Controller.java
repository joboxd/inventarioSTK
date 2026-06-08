package com.jacob.inventario.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    

    @PreAuthorize("hasRole('client')")
    @GetMapping("/liveness")
    public String liveness(){
        return "ok";
    }
    
}
