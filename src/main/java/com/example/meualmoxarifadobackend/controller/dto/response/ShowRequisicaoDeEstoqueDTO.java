package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoque;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowRequisicaoDeEstoqueDTO(
        Long id,
        LocalDateTime dataRequisicao,
        BigDecimal valorTotal,
        String obs,
        String ordemProducao,
        Long idRequisitante,
        Long idLocalDeAplicacao,
        List<ShowRequisicaoEstoqueItemDTO> itens) {
    public ShowRequisicaoDeEstoqueDTO(RequisicaoEstoque model) {
        this(
                model.getId(),
                model.getDataRequisicao(),
                model.getValorTotal(),
                model.getOrdemProducao(),
                model.getObs(),
                model.getRequisitante().getId(),
                model.getEquipamento().getId(),
                ofNullable(model.getItens()).orElse(emptyList()).stream().map(ShowRequisicaoEstoqueItemDTO::new).collect(toList())
        );
    }

}
