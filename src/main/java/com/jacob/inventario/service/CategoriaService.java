package com.jacob.inventario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacob.inventario.entity.CategoriaEntity;
import com.jacob.inventario.repository.CategoriaRepository;
import com.jacob.inventario.utils.CustomExcepcion;
import com.jacob.inventario.utils.EnumErrorsCodes;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Long save(CategoriaEntity categoriaEntity) {
        try {
            CategoriaEntity saved = categoriaRepository.save(categoriaEntity);
            if (Objects.isNull(saved)) {
                throw new CustomExcepcion(EnumErrorsCodes.INVALID_INPUT);
            }
            return saved.getId();
        } catch (Exception e) {
            throw new CustomExcepcion(EnumErrorsCodes.DATABASE_ERROR);
        }
    }

    public CategoriaEntity findById(Long id) {
        Optional<CategoriaEntity> found = categoriaRepository.findById(id);
        return found.get();
    }

    public List<CategoriaEntity> findAll() {
        List<CategoriaEntity> list = new ArrayList<>();
        Iterable<CategoriaEntity> found = categoriaRepository.findAll();
        for (CategoriaEntity categoriaEntity : found) {
            list.add(categoriaEntity);
        }
        return list;
    }
}
