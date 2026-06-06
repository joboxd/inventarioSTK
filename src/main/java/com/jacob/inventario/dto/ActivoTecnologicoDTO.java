package com.jacob.inventario.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jacob.inventario.utils.Estados;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivoTecnologicoDTO {
    private UUID id;
    private String numeroSerie;
    private String marcaModelo;
    private Estados estado;
    private BigDecimal costoAdquisicion;
    private LocalDateTime fechaIngreso;
    private String categoria;
    private String folioInventario;
}
