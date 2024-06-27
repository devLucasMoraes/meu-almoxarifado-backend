package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.Equipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface EquipamentoService extends CrudService<Long, Equipamento>{
    Boolean existsById(Long id);

    Page<Equipamento> dynamicFindAll(Specification<Equipamento> spec, Pageable pageable);

}
