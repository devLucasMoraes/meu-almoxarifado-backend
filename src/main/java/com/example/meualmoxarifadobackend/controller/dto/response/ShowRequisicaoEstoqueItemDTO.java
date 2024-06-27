package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoqueItem;
import com.example.meualmoxarifadobackend.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowRequisicaoEstoqueItemDTO(
        Long idItem,
        Long idMaterial,
        Unidade undConsumo,
        BigDecimal quantEntregue,
        BigDecimal valorUntEntregue) {
    public ShowRequisicaoEstoqueItemDTO(RequisicaoEstoqueItem model) {
        this(
                model.getId(),
                model.getInsumo().getId(),
                model.getUnidade(),
                model.getQuantidade(),
                model.getValorUnitario()
        );
    }
}
