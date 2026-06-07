package com.jacob.inventario.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacob.inventario.dto.ActivoTecnologicoDTO;
import com.jacob.inventario.entity.ActivoTecnologicoEntity;
import com.jacob.inventario.service.ActivoTecnologicoService;
import com.jacob.inventario.utils.Estados;

@RestController
@RequestMapping("/api/activos")
public class ControllerActivos {
    private final ActivoTecnologicoService activoTecnologicoService;

    @Autowired
    public ControllerActivos(ActivoTecnologicoService activoTecnologicoService) {
        this.activoTecnologicoService = activoTecnologicoService;
    }

    @PreAuthorize("hasRole('client')")
    @GetMapping("/liveness")
    public String liveness() {
        return "ok";
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{id}")
    public ActivoTecnologicoEntity activoById(@PathVariable("id") UUID id) {
        return activoTecnologicoService.getById(id);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/serie/{serie}")
    public ActivoTecnologicoEntity activoByNumeroSerie(@PathVariable("serie") String serie) {
        return activoTecnologicoService.getByNumeroSerie(serie);
    }
     @PreAuthorize("hasRole('admin')")
    @GetMapping("/marca/{marca}")
    public List<ActivoTecnologicoEntity> activoByModeloMarca(@PathVariable("marca") String marca) {
        return activoTecnologicoService.getByMarcaModelo(marca);
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/categoria/{categoria}")
    public List<ActivoTecnologicoEntity> activoByCategoria(@PathVariable("categoria") String categoria) {
        return activoTecnologicoService.getByCategoria(categoria);
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/rango/{inicio}/{fin}")
    public List<ActivoTecnologicoEntity> activoByRango(@PathVariable("inicio") BigDecimal inicio, @PathVariable("fin") BigDecimal fin) {
        return activoTecnologicoService.getByRango(inicio,fin);
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/save")
    public UUID saveActivo(@RequestBody ActivoTecnologicoEntity body) {
        return activoTecnologicoService.save(body);
    }
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/update")
    public UUID updateActivo(@RequestBody ActivoTecnologicoEntity body) {
        return activoTecnologicoService.save(body);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/reporte")
    public Map<String, Object> generarReporte(
            @RequestParam(value = "numeroSerie", required = false) String numeroSerie,
            @RequestParam(value = "marcaModelo", required = false) String marcaModelo,
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "estado", required = false) Estados estado,
            @RequestParam(value = "minCosto", required = false) BigDecimal minCosto,
            @RequestParam(value = "maxCosto", required = false) BigDecimal maxCosto) {
        return activoTecnologicoService.generarReporteZip(numeroSerie, marcaModelo, categoria, estado, minCosto, maxCosto);
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/filtros")
    public Page<ActivoTecnologicoDTO> findByFiltersPage(
            @RequestParam(value = "numeroSerie", required = false) String numeroSerie,
            @RequestParam(value = "marcaModelo", required = false) String marcaModelo,
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "estado", required = false) Estados estado,
            @RequestParam(value = "minCosto", required = false) BigDecimal minCosto,
            @RequestParam(value = "maxCosto", required = false) BigDecimal maxCosto, Pageable pageable) {
        return activoTecnologicoService.findByFiltersPage(numeroSerie, marcaModelo, categoria, estado, minCosto, maxCosto, pageable);
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public Page<ActivoTecnologicoDTO> getAll(Pageable pageable) {
        return activoTecnologicoService.getAll(pageable);
    }
}
