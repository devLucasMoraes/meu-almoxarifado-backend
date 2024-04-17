package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import com.example.meualmoxarifadobackend.domain.model.NfeDeCompra;
import com.example.meualmoxarifadobackend.domain.model.Transportadora;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record NfeDeCompraDTO(
        Long id,
        String nfe,
        String chaveNfe,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataEmissao,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataRecebimento,
        BigDecimal valorFrete,
        BigDecimal valorSeguro,
        BigDecimal valorDesconto,
        BigDecimal valorOutros,
        BigDecimal valorTotalIpi,
        BigDecimal valorTotalProdutos,
        BigDecimal valorTotalNfe,
        String obs,
        @NotNull
        Long idTransportadora,
        @NotNull
        Long idFornecedora,
        @Valid
        @NotNull
        @NotEmpty
        List<ItemDeCompraDTO> itens) {
    public NfeDeCompra toModel() {
        NfeDeCompra model = new NfeDeCompra();
        model.setId(this.id);
        model.setNfe(this.nfe);
        model.setChaveNfe(this.chaveNfe);
        model.setDataEmissao(this.dataEmissao);
        model.setDataRecebimento(this.dataRecebimento);
        model.setValorTotalProdutos(this.valorTotalProdutos);
        model.setValorFrete(this.valorFrete);
        model.setValorTotalIpi(this.valorTotalIpi);
        model.setValorSeguro(this.valorSeguro);
        model.setValorDesconto(this.valorDesconto);
        model.setValorTotalNfe(this.valorTotalNfe);
        model.setValorOutros(this.valorOutros);
        model.setObs(this.obs);
        model.setTransportadora(new Transportadora(this.idTransportadora));
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setItens(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(ItemDeCompraDTO::toModel).collect(toList()));
        return model;
    }
    public NfeDeCompra toNewModel() {
        NfeDeCompra model = new NfeDeCompra();
        model.setNfe(this.nfe);
        model.setChaveNfe(this.chaveNfe);
        model.setDataEmissao(this.dataEmissao);
        model.setDataRecebimento(this.dataRecebimento);
        model.setValorTotalProdutos(this.valorTotalProdutos);
        model.setValorFrete(this.valorFrete);
        model.setValorTotalIpi(this.valorTotalIpi);
        model.setValorSeguro(this.valorSeguro);
        model.setValorDesconto(this.valorDesconto);
        model.setValorTotalNfe(this.valorTotalNfe);
        model.setValorOutros(this.valorOutros);
        model.setObs(this.obs);
        model.setTransportadora(new Transportadora(this.idTransportadora));
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setItens(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(ItemDeCompraDTO::toNewModel).collect(toList()));
        return model;
    }
}
