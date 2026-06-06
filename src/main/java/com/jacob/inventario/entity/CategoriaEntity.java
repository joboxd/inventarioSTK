package com.jacob.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo_prefijo", nullable = false, length = 3, unique = true)
    private String codigoPrefijo;

    @jakarta.persistence.PrePersist
    protected void prePersist() {
        if (this.codigoPrefijo == null && this.nombre != null) {
            this.codigoPrefijo = this.nombre.substring(0, Math.min(this.nombre.length(), 3)).toUpperCase();
        }
    }
}
