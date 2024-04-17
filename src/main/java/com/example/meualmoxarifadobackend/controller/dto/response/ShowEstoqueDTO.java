package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.Material;

import java.math.BigDecimal;

public record ShowEstoqueDTO(
        Long id,
        String descricao,
        BigDecimal qtdEmEstoque,
        BigDecimal valorUnt,
        BigDecimal valorTotal,
        Boolean abaixoMinimo,
        Long idCategoria
) {
    public ShowEstoqueDTO(Material model) {
        this(
                model.getId(),
                model.getDescricao(),
                model.getQtdEmEstoqueFisico(),
                model.getValorUntMed(),
                model.getValorUntMed().multiply(model.getQtdEmEstoqueFisico()),
                model.getQtdEmEstoqueFisico().compareTo(model.getCategoria().getEstoqueMinimo()) < 0,
                model.getCategoria().getId()
        );
    }
}
