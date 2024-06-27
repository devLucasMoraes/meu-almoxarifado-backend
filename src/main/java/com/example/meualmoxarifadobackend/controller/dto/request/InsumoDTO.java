package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Categoria;
import com.example.meualmoxarifadobackend.domain.model.Insumo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InsumoDTO(
        Long id,
        @NotBlank
        String descricao,
        @NotNull
        Boolean valorUntMedAuto,
        BigDecimal valorUntMed,
        @NotNull
        Long idCategoria
) {
    public Insumo toModel() {
        Insumo model = new Insumo();
        model.setId(this.id);
        model.setDescricao(this.descricao);
        model.setValorUntMedAuto(this.valorUntMedAuto);
        if(!this.valorUntMedAuto) {
            model.setValorUntMed(this.valorUntMed);
        }
        model.setCategoria(new Categoria(this.idCategoria));

        return model;
    }
}
