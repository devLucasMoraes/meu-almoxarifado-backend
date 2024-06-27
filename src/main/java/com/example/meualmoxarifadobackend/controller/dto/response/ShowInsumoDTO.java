package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.Insumo;

import java.math.BigDecimal;

public record ShowInsumoDTO(
        Long id,
        String descricao,
        Boolean valorUntMedAuto,
        BigDecimal valorUnt,
        Long idCategoria) {
    public ShowInsumoDTO(Insumo model) {
        this(
                model.getId(),
                model.getDescricao(),
                model.getValorUntMedAuto(),
                model.getValorUntMed(),
                model.getCategoria().getId()
        );
    }
}
