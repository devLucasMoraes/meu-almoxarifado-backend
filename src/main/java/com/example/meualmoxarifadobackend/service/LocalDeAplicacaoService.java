package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.LocalDeAplicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface LocalDeAplicacaoService extends CrudService<Long, LocalDeAplicacao>{
    Boolean existsById(Long id);

    Page<LocalDeAplicacao> dynamicFindAll(Specification<LocalDeAplicacao> spec, Pageable pageable);

}
