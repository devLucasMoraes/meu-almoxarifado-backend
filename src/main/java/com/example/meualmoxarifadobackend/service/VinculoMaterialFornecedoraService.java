package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.VinculoMaterialFornecedora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface VinculoMaterialFornecedoraService extends CrudService<Long, VinculoMaterialFornecedora>{
    Boolean existsById(Long id);

    Page<VinculoMaterialFornecedora> dynamicFindAll(Specification<VinculoMaterialFornecedora> spec, Pageable pageable);

    VinculoMaterialFornecedora dynamicFindOne(Specification<VinculoMaterialFornecedora> spec);
}
