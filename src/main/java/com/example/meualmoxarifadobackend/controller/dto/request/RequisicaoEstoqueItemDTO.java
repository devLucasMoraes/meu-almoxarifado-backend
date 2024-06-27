package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoqueItem;
import com.example.meualmoxarifadobackend.domain.model.Insumo;
import com.example.meualmoxarifadobackend.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RequisicaoEstoqueItemDTO(
        Long idItem,
        @NotNull
        Long idInsumo,
        @NotNull
        Unidade undConsumo,
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue) {
    public RequisicaoEstoqueItem toModel() {
        RequisicaoEstoqueItem model = new RequisicaoEstoqueItem();
        model.setId(this.idItem);
        model.setInsumo(new Insumo(this.idInsumo));
        model.setUnidade(this.undConsumo);
        model.setQuantidade(this.quantEntregue);
        return model;
    }

    public RequisicaoEstoqueItem toNewModel() {
        RequisicaoEstoqueItem model = new RequisicaoEstoqueItem();
        model.setInsumo(new Insumo(this.idInsumo));
        model.setUnidade(this.undConsumo);
        model.setQuantidade(this.quantEntregue);
        return model;
    }
}
