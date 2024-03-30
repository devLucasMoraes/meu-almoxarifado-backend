package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MaterialService extends CrudService<Long, Material>{
    Boolean existsById(Long id);

    Page<Material> dynamicFindAll(Specification<Material> spec, Pageable pageable);
}
