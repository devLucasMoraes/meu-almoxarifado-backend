package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.VinculoMaterialFornecedora;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowVinculoMaterialFornecedoraDTO(
        Long idVinculo,
        Long idFornecedora,
        Long idMaterial,
        String referenciaFornecedora,
        String descricaoFornecedora,
        List<ShowConversaoDeCompraDTO> conversoesDeCompra) {

    public ShowVinculoMaterialFornecedoraDTO(VinculoMaterialFornecedora model) {
        this(
                model.getId(),
                model.getFornecedora().getId(),
                model.getMaterial().getId(),
                model.getReferenciaFornecedora(),
                model.getDescricaoFornecedora(),
                ofNullable(model.getConversaoDeCompras()).orElse(emptyList()).stream().map(ShowConversaoDeCompraDTO::new).collect(toList())

        );
    }
}
