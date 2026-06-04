package com.jacob.inventario.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacob.inventario.entity.ActivoTecnologicoEntity;
import com.jacob.inventario.repository.ActivoTecnologicoRepository;
import com.jacob.inventario.utils.CustomExcepcion;
import com.jacob.inventario.utils.EnumErrorsCodes;
import com.jacob.inventario.utils.Estados;

@Service
public class ActivoTecnologicoService {
    private final ActivoTecnologicoRepository activoTecnologicoRepository;

    @Autowired
    public ActivoTecnologicoService(ActivoTecnologicoRepository activoTecnologicoRepository) {
        this.activoTecnologicoRepository = activoTecnologicoRepository;
    }

    public ActivoTecnologicoEntity getById(UUID id) {
        try {
            Optional<ActivoTecnologicoEntity> activoTecnologicoEntity = activoTecnologicoRepository.findById(id);
            if (activoTecnologicoEntity.isEmpty()) {
                throw new CustomExcepcion(EnumErrorsCodes.ENTITY_NOT_FOUND);
            }
            return activoTecnologicoEntity.get();
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public UUID save(ActivoTecnologicoEntity asset) {
        try {
            ActivoTecnologicoEntity found = getById(asset.getId());
            if (found.getEstado().equals("Baja") && !asset.getEstado().equals("Baja")) {
                throw new CustomExcepcion(EnumErrorsCodes.INVALID_INPUT);
            }
            asset.setFolioInventario(asset.getCategoria().getCodigoPrefijo() + asset.getFechaIngreso().getYear()
                    + asset.getCategoria().getId());
            ActivoTecnologicoEntity activoTecnologicoEntity = activoTecnologicoRepository.save(asset);
            if (Objects.isNull(activoTecnologicoEntity)) {
                throw new CustomExcepcion(EnumErrorsCodes.DUPLICATE_ENTITY);
            }
            return activoTecnologicoEntity.getId();
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public ActivoTecnologicoEntity getByNumeroSerie(String serie) {
        try {
            Optional<ActivoTecnologicoEntity> activoTecnologicoEntity = activoTecnologicoRepository
                    .findByNumeroSerie(serie);
            if (activoTecnologicoEntity.isEmpty()) {
                throw new CustomExcepcion(EnumErrorsCodes.ENTITY_NOT_FOUND);
            }
            return activoTecnologicoEntity.get();
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public List<ActivoTecnologicoEntity> getByMarcaModelo(String marca) {
        try {
            List<ActivoTecnologicoEntity> activoTecnologicoEntity = activoTecnologicoRepository
                    .findByMarcaModelo(marca);
            if (activoTecnologicoEntity.isEmpty()) {
                throw new CustomExcepcion(EnumErrorsCodes.ENTITY_NOT_FOUND);
            }
            return activoTecnologicoEntity;
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public List<ActivoTecnologicoEntity> getByCategoria(String categoria) {
        try {
            List<ActivoTecnologicoEntity> activoTecnologicoEntity = activoTecnologicoRepository
                    .findByCategoria(categoria);
            if (activoTecnologicoEntity.isEmpty()) {
                throw new CustomExcepcion(EnumErrorsCodes.ENTITY_NOT_FOUND);
            }
            return activoTecnologicoEntity;
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public List<ActivoTecnologicoEntity> getByRango(BigDecimal inicio, BigDecimal fin) {
        try {
            List<ActivoTecnologicoEntity> activoTecnologicoEntity = activoTecnologicoRepository
                    .findByCostoAdquisicionBetween(inicio, fin);
            if (activoTecnologicoEntity.isEmpty()) {
                throw new CustomExcepcion(EnumErrorsCodes.ENTITY_NOT_FOUND);
            }
            return activoTecnologicoEntity;
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public Map<String, Object> generarReporteZip(String numeroSerie, String marcaModelo, String categoria, Estados estado, BigDecimal minCosto, BigDecimal maxCosto) {
        try {
            String ns = (numeroSerie != null && !numeroSerie.trim().isEmpty()) ? numeroSerie.trim() : null;
            String mm = (marcaModelo != null && !marcaModelo.trim().isEmpty()) ? marcaModelo.trim() : null;
            String cat = (categoria != null && !categoria.trim().isEmpty()) ? categoria.trim() : null;

            List<ActivoTecnologicoEntity> activos = activoTecnologicoRepository.findByFilters(ns, mm, cat, estado, minCosto, maxCosto);

            byte[] excelBytes;
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Activos");

                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);

                Row headerRow = sheet.createRow(0);
                String[] columns = {"ID", "Folio Inventario", "Número de Serie", "Marca/Modelo", "Estado", "Costo Adquisición", "Fecha Ingreso", "Categoría"};
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                }

                CellStyle costStyle = workbook.createCellStyle();
                DataFormat format = workbook.createDataFormat();
                costStyle.setDataFormat(format.getFormat("$#,##0.00"));

                int rowNum = 1;
                for (ActivoTecnologicoEntity activo : activos) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(activo.getId() != null ? activo.getId().toString() : "");
                    row.createCell(1).setCellValue(activo.getFolioInventario());
                    row.createCell(2).setCellValue(activo.getNumeroSerie());
                    row.createCell(3).setCellValue(activo.getMarcaModelo());
                    row.createCell(4).setCellValue(activo.getEstado() != null ? activo.getEstado().name() : "");
                    
                    Cell costCell = row.createCell(5);
                    if (activo.getCostoAdquisicion() != null) {
                        costCell.setCellValue(activo.getCostoAdquisicion().doubleValue());
                        costCell.setCellStyle(costStyle);
                    } else {
                        costCell.setCellValue(0.0);
                    }
                    
                    row.createCell(6).setCellValue(activo.getFechaIngreso() != null ? activo.getFechaIngreso().toString() : "");
                    row.createCell(7).setCellValue(activo.getCategoria() != null ? activo.getCategoria().getNombre() : "");
                }

                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                    workbook.write(bos);
                    excelBytes = bos.toByteArray();
                }
            }

            byte[] zipBytes;
            try (ByteArrayOutputStream zipBos = new ByteArrayOutputStream();
                 ZipOutputStream zos = new ZipOutputStream(zipBos)) {
                
                ZipEntry entry = new ZipEntry("reporte_activos.xlsx");
                zos.putNextEntry(entry);
                zos.write(excelBytes);
                zos.closeEntry();
                zos.finish();
                
                zipBytes = zipBos.toByteArray();
            }

            String base64Zip = Base64.getEncoder().encodeToString(zipBytes);

            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "Reporte generado correctamente");
            response.put("fileName", "inventario.zip");
            response.put("fileBase64", base64Zip);

            return response;
        } catch (IOException e) {
            throw new CustomExcepcion(EnumErrorsCodes.INTERNAL_SERVER_ERROR, "Error al generar el archivo del reporte: " + e.getMessage());
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR, "Error al consultar la base de datos para el reporte: " + e.getMessage());
        }
    }
}
