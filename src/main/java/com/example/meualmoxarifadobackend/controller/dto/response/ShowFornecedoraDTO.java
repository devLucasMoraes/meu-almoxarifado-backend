package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.Fornecedora;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowFornecedoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone,
        List<ShowVinculoMaterialFornecedoraDTO> materiaisVinculados
) {
    public ShowFornecedoraDTO(Fornecedora model) {
        this(
                model.getId(),
                model.getCnpj(),
                model.getRazaoSocial(),
                model.getNomeFantasia(),
                model.getFone(),
                ofNullable(model.getFornecedorasVinculadas()).orElse(emptyList()).stream().map(ShowVinculoMaterialFornecedoraDTO::new).collect(toList())

        );
    }
}
