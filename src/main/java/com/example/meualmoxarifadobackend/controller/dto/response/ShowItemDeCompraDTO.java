package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.ItemDeCompra;
import com.example.meualmoxarifadobackend.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemDeCompraDTO(
        Long idItem,
        Long idMaterial,
        Unidade undCom,
        BigDecimal quantCom,
        BigDecimal valorUntCom,
        BigDecimal valorIpi,
        String descricaoFornecedora,
        String referenciaFornecedora) {
    public ShowItemDeCompraDTO(ItemDeCompra model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUnidade(),
                model.getQuantidade(),
                model.getValorUnitario(),
                model.getValorIpi(),
                model.getDescricaoFornecedora(),
                model.getReferenciaFornecedora()
        );
    }
}
