package com.jacob.inventario.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jacob.inventario.entity.CategoriaEntity;

@Repository
public interface CategoriaRepository extends CrudRepository<CategoriaEntity,Long>{
    
}
