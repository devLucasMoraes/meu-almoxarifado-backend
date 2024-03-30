package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Emprestimo;
import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import com.example.meualmoxarifadobackend.domain.model.Situacao;
import com.example.meualmoxarifadobackend.domain.model.Tipo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record EmprestimoAPagarDTO(
        Long id,
        @NotNull
        Tipo tipo,
        @NotNull
        LocalDateTime dataDeAbertura,
        BigDecimal valorTotal,
        @NotNull
        Long idFornecedora,
        Situacao situacao,
        Boolean atribuirAoEstoqueFisico,
        @Valid
        @NotNull
        List<ItemEmprestimoDTO> itensAReceber,
        @Valid
        List<ItemEmprestimoDTO> itensAPagar

) {

    public Emprestimo toModel() {
        Emprestimo model = new Emprestimo();
        model.setId(this.id);
        model.setTipo(this.tipo);
        model.setDataDeAbertura(this.dataDeAbertura);
        model.setValorTotal(this.valorTotal);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setSituacao(this.situacao);
        model.setAtribuirAoEstoqueFisico(this.atribuirAoEstoqueFisico);
        model.setItensAReceber(ofNullable(this.itensAReceber)
                .orElse(emptyList())
                .stream().map(ItemEmprestimoDTO::aReceberToModel).collect(toList()));
        model.setItensAPagar(ofNullable(this.itensAPagar)
                .orElse(emptyList())
                .stream().map(ItemEmprestimoDTO::aPagarToModel).collect(toList()));
        return model;
    }

    public Emprestimo toNewModel() {
        Emprestimo model = new Emprestimo();
        model.setTipo(this.tipo);
        model.setDataDeAbertura(this.dataDeAbertura);
        model.setValorTotal(this.valorTotal);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setSituacao(this.situacao);
        model.setAtribuirAoEstoqueFisico(this.atribuirAoEstoqueFisico);
        model.setItensAReceber(ofNullable(this.itensAReceber)
                .orElse(emptyList())
                .stream().map(ItemEmprestimoDTO::aReceberToNewModel).collect(toList()));
        model.setItensAPagar(ofNullable(this.itensAPagar)
                .orElse(emptyList())
                .stream().map(ItemEmprestimoDTO::aPagarToNewModel).collect(toList()));
        return model;
    }

}
