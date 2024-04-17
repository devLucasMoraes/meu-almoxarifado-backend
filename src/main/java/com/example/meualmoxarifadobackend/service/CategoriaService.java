package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CategoriaService extends CrudService<Long, Categoria>{
    Boolean existsById(Long id);
    Page<Categoria> dynamicFindAll(Specification<Categoria> spec, Pageable pageable);
}
