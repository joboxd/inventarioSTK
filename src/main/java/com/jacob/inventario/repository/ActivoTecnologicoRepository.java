package com.jacob.inventario.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jacob.inventario.entity.ActivoTecnologicoEntity;
import com.jacob.inventario.utils.Estados;

@Repository
public interface ActivoTecnologicoRepository extends CrudRepository<ActivoTecnologicoEntity, UUID> {

       Optional<ActivoTecnologicoEntity> findById(UUID id);

       Optional<ActivoTecnologicoEntity> findByNumeroSerie(String serie);

       List<ActivoTecnologicoEntity> findByMarcaModelo(String marca);

       List<ActivoTecnologicoEntity> findByCategoria(String categoria);

       List<ActivoTecnologicoEntity> findByCostoAdquisicionBetween(BigDecimal inicio, BigDecimal fin);

       @Query("SELECT a FROM ActivoTecnologicoEntity a " +
                     "WHERE (:numeroSerie IS NULL OR a.numeroSerie = :numeroSerie) " +
                     "AND (:marcaModelo IS NULL OR LOWER(a.marcaModelo) LIKE :marcaModelo) " +
                     "AND (:categoria IS NULL OR LOWER(a.categoria.nombre) LIKE :categoria) " +
                     "AND (:estado IS NULL OR a.estado = :estado) " +
                     "AND (:minCosto IS NULL OR a.costoAdquisicion >= :minCosto) " +
                     "AND (:maxCosto IS NULL OR a.costoAdquisicion <= :maxCosto)")
       List<ActivoTecnologicoEntity> findByFilters(
                     @Param("numeroSerie") String numeroSerie,
                     @Param("marcaModelo") String marcaModelo,
                     @Param("categoria") String categoria,
                     @Param("estado") Estados estado,
                     @Param("minCosto") BigDecimal minCosto,
                     @Param("maxCosto") BigDecimal maxCosto);

       @Query("SELECT a FROM ActivoTecnologicoEntity a " +
                     "WHERE (:numeroSerie IS NULL OR a.numeroSerie = :numeroSerie) " +
                     "AND (:marcaModelo IS NULL OR LOWER(a.marcaModelo) LIKE :marcaModelo) " +
                     "AND (:categoria IS NULL OR LOWER(a.categoria.nombre) LIKE :categoria) " +
                     "AND (:estado IS NULL OR a.estado = :estado) " +
                     "AND (:minCosto IS NULL OR a.costoAdquisicion >= :minCosto) " +
                     "AND (:maxCosto IS NULL OR a.costoAdquisicion <= :maxCosto)")
       Page<ActivoTecnologicoEntity> findByFiltersPage(
                     @Param("numeroSerie") String numeroSerie,
                     @Param("marcaModelo") String marcaModelo,
                     @Param("categoria") String categoria,
                     @Param("estado") Estados estado,
                     @Param("minCosto") BigDecimal minCosto,
                     @Param("maxCosto") BigDecimal maxCosto, Pageable pageable);

       Page<ActivoTecnologicoEntity> findAll(Pageable pageable);

       @Query("SELECT COUNT(a) FROM ActivoTecnologicoEntity a")
       long contarTotalActivos();
}
