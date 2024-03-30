package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import com.example.meualmoxarifadobackend.domain.model.Material;
import com.example.meualmoxarifadobackend.domain.model.VinculoMaterialFornecedora;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record VinculoMaterialFornecedoraDTO(
        Long idVinculo,
        @NotNull
        Long idFornecedora,
        Long idMaterial,
        @NotBlank
        String referenciaFornecedora,
        String descricaoFornecedora,
        @Valid
        List<ConversaoDeCompraDTO> conversoesDeCompra) {
    public VinculoMaterialFornecedora toModel() {
        VinculoMaterialFornecedora model = new VinculoMaterialFornecedora();
        model.setId(this.idVinculo);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setMaterial(new Material(this.idMaterial));
        model.setReferenciaFornecedora(this.referenciaFornecedora);
        model.setDescricaoFornecedora(this.descricaoFornecedora);
        model.setConversaoDeCompras(ofNullable(this.conversoesDeCompra)
                .orElse(emptyList())
                .stream().map(ConversaoDeCompraDTO::toModel).collect(toList()));
        return model;
    }
}
