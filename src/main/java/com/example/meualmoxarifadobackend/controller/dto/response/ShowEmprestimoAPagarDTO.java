package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.Emprestimo;
import com.example.meualmoxarifadobackend.domain.model.Situacao;
import com.example.meualmoxarifadobackend.domain.model.Tipo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowEmprestimoAPagarDTO(
        Long id,
        Tipo tipo,
        LocalDateTime dataDeAbertura,
        BigDecimal valorTotal,
        Long idFornecedora,
        Situacao situacao,
        Boolean atribuirAoEstoqueFisico,
        List<ShowItemEmprestimoAPagarDTO> itensAPagar,
        List<ShowItemEmprestimoAReceberDTO> itensAReceber

) {
    public ShowEmprestimoAPagarDTO(Emprestimo model) {
        this(
                model.getId(),
                model.getTipo(),
                model.getDataDeAbertura(),
                model.getValorTotal(),
                model.getFornecedora().getId(),
                model.getSituacao(),
                model.getAtribuirAoEstoqueFisico(),
                ofNullable(model.getItensAPagar()).orElse(emptyList()).stream().map(ShowItemEmprestimoAPagarDTO::new).collect(toList()),
                ofNullable(model.getItensAReceber()).orElse(emptyList()).stream().map(ShowItemEmprestimoAReceberDTO::new).collect(toList())
        );
    }
}
