package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface FornecedoraService extends CrudService<Long, Fornecedora>{
    Boolean existsById(Long id);

    Page<Fornecedora> dynamicFindAll(Specification<Fornecedora> spec, Pageable pageable);

    Fornecedora getByCnpj(String cnpj);
}
