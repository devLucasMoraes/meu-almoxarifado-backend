package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.ItemEmprestimoAPagar;
import com.example.meualmoxarifadobackend.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemEmprestimoAPagarDTO(
        Long idItem,
        Long idMaterial,
        Unidade unidade,
        BigDecimal quantEntregue,
        BigDecimal valorUnt) {
    public ShowItemEmprestimoAPagarDTO(ItemEmprestimoAPagar model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUnidade(),
                model.getQuantidade(),
                model.getValorUnitario()
        );
    }
}
