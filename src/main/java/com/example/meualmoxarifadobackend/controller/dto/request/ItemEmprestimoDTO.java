package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.ItemEmprestimoAPagar;
import com.example.meualmoxarifadobackend.domain.model.ItemEmprestimoAReceber;
import com.example.meualmoxarifadobackend.domain.model.Material;
import com.example.meualmoxarifadobackend.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemEmprestimoDTO(
        Long idItem,
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade unidade,
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue,
        @Positive(message = "deve ser maior que zero")
        BigDecimal valorUnt) {
    public ItemEmprestimoAReceber aReceberToModel() {
        ItemEmprestimoAReceber model = new ItemEmprestimoAReceber();
        model.setId(this.idItem);
        model.setMaterial(new Material(this.idMaterial));
        model.setUnidade(this.unidade);
        model.setQuantidade(this.quantEntregue);
        model.setValorUnitario(this.valorUnt);
        return model;
    }

    public ItemEmprestimoAReceber aReceberToNewModel() {
        ItemEmprestimoAReceber model = new ItemEmprestimoAReceber();
        model.setMaterial(new Material(this.idMaterial));
        model.setUnidade(this.unidade);
        model.setQuantidade(this.quantEntregue);
        model.setValorUnitario(this.valorUnt);
        return model;
    }

    public ItemEmprestimoAPagar aPagarToModel() {
        var model = new ItemEmprestimoAPagar();
        model.setId(this.idItem);
        model.setMaterial(new Material(this.idMaterial));
        model.setUnidade(this.unidade);
        model.setQuantidade(this.quantEntregue);
        model.setValorUnitario(this.valorUnt);
        return model;
    }

    public ItemEmprestimoAPagar aPagarToNewModel() {
        var model = new ItemEmprestimoAPagar();
        model.setMaterial(new Material(this.idMaterial));
        model.setUnidade(this.unidade);
        model.setQuantidade(this.quantEntregue);
        model.setValorUnitario(this.valorUnt);
        return model;
    }
}
