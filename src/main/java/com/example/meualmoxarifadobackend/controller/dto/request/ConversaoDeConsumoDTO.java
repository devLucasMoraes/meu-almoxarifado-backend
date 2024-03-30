package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.ConversaoDeConsumo;
import com.example.meualmoxarifadobackend.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConversaoDeConsumoDTO(
        Long idConversao,
        @NotNull
        Unidade undConsumo,
        Unidade undEstoque,
        @NotNull
        BigDecimal fatorDeConversao) {
    public ConversaoDeConsumo toModel(){
        ConversaoDeConsumo model = new ConversaoDeConsumo();
        model.setId(this.idConversao);
        model.setUndConsumo(this.undConsumo);
        model.setUndEstoque(this.undEstoque);
        model.setFatorDeConversao(this.fatorDeConversao);
        return model;
    }
}
