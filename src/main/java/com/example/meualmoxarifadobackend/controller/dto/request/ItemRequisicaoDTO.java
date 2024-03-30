package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.ItemRequisicao;
import com.example.meualmoxarifadobackend.domain.model.Material;
import com.example.meualmoxarifadobackend.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemRequisicaoDTO(
        Long idItem,
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade undConsumo,
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue) {
    public ItemRequisicao toModel() {
        ItemRequisicao model = new ItemRequisicao();
        model.setId(this.idItem);
        model.setMaterial(new Material(this.idMaterial));
        model.setUnidade(this.undConsumo);
        model.setQuantidade(this.quantEntregue);
        return model;
    }

    public ItemRequisicao toNewModel() {
        ItemRequisicao model = new ItemRequisicao();
        model.setMaterial(new Material(this.idMaterial));
        model.setUnidade(this.undConsumo);
        model.setQuantidade(this.quantEntregue);
        return model;
    }
}
