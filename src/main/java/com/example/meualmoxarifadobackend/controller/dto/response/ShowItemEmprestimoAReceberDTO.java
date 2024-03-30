package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.ItemEmprestimoAReceber;
import com.example.meualmoxarifadobackend.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemEmprestimoAReceberDTO(
        Long idItem,
        Long idMaterial,
        Unidade unidade,
        BigDecimal quantEntregue,
        BigDecimal valorUnt) {
    public ShowItemEmprestimoAReceberDTO(ItemEmprestimoAReceber model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUnidade(),
                model.getQuantidade(),
                model.getValorUnitario()
        );
    }
}
