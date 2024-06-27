package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.domain.model.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface InsumoService extends CrudService<Long, Insumo>{
    Boolean existsById(Long id);

    Page<Insumo> dynamicFindAll(Specification<Insumo> spec, Pageable pageable);

    void acertarEstoque(AcertoEstoqueDTO acertoEstoqueDTO);
}
