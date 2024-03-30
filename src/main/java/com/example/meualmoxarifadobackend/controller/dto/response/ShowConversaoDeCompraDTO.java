package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.ConversaoDeCompra;
import com.example.meualmoxarifadobackend.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowConversaoDeCompraDTO(
        Long idConversao,
        Unidade undCompra,
        Unidade undEstoque,
        BigDecimal fatorDeConversao) {
    public ShowConversaoDeCompraDTO(ConversaoDeCompra model) {
        this(model.getId(), model.getUndCompra(), model.getUndEstoque(), model.getFatorDeConversao());
    }
}
