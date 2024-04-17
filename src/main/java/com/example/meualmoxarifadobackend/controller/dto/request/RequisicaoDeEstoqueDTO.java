package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.LocalDeAplicacao;
import com.example.meualmoxarifadobackend.domain.model.RequisicaoDeEstoque;
import com.example.meualmoxarifadobackend.domain.model.Requisitante;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record RequisicaoDeEstoqueDTO(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataRequisicao,
        String obs,
        String ordemProducao,
        @NotNull
        Long idRequisitante,
        @NotNull
        Long idLocalDeAplicacao,
        @Valid
        @NotNull
        @NotEmpty
        List<ItemRequisicaoDTO> itens) {
    public RequisicaoDeEstoque toModel() {
        RequisicaoDeEstoque model = new RequisicaoDeEstoque();
        model.setId(this.id);
        model.setDataRequisicao(this.dataRequisicao);
        model.setObs(this.obs);
        model.setOrdemProducao(this.ordemProducao);
        model.setLocalDeAplicacao(new LocalDeAplicacao(this.idLocalDeAplicacao));
        model.setRequisitante(new Requisitante(this.idRequisitante));
        model.setItens(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(ItemRequisicaoDTO::toModel).collect(toList()));
        return model;
    }

    public RequisicaoDeEstoque toNewModel() {
        RequisicaoDeEstoque model = new RequisicaoDeEstoque();
        model.setDataRequisicao(this.dataRequisicao);
        model.setObs(this.obs);
        model.setOrdemProducao(this.ordemProducao);
        model.setLocalDeAplicacao(new LocalDeAplicacao(this.idLocalDeAplicacao));
        model.setRequisitante(new Requisitante(this.idRequisitante));
        model.setItens(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(ItemRequisicaoDTO::toNewModel).collect(toList()));
        return model;
    }
}
