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

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/liveness")
    public String liveness() {
        return "ok";
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/{id}")
    public ActivoTecnologicoEntity activoById(@PathVariable("id") UUID id) {
        return activoTecnologicoService.getById(id);
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/serie/{serie}")
    public ActivoTecnologicoDTO activoByNumeroSerie(@PathVariable("serie") String serie) {
        return activoTecnologicoService.getByNumeroSerie(serie);
    }
     @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/marca/{marca}")
    public List<ActivoTecnologicoEntity> activoByModeloMarca(@PathVariable("marca") String marca) {
        return activoTecnologicoService.getByMarcaModelo(marca);
    }
    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/categoria/{categoria}")
    public List<ActivoTecnologicoEntity> activoByCategoria(@PathVariable("categoria") String categoria) {
        return activoTecnologicoService.getByCategoria(categoria);
    }
    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/rango/{inicio}/{fin}")
    public List<ActivoTecnologicoEntity> activoByRango(@PathVariable("inicio") BigDecimal inicio, @PathVariable("fin") BigDecimal fin) {
        return activoTecnologicoService.getByRango(inicio,fin);
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/save")
    public UUID saveActivo(@RequestBody ActivoTecnologicoDTO body) {
        return activoTecnologicoService.save(body);
    }
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/update")
    public UUID updateActivo(@RequestBody ActivoTecnologicoDTO body) {
        return activoTecnologicoService.update(body);
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
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
    @PreAuthorize("hasAnyRole('admin', 'user')")
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
    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/all")
    public Page<ActivoTecnologicoDTO> getAll(Pageable pageable) {
        return activoTecnologicoService.getAll(pageable);
    }
}
