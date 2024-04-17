package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.domain.model.*;

public interface MovimentacaoFactory {

    Movimentacao criarMovimentacaoAcertoEstoque(AcertoEstoqueDTO acerto);

    Movimentacao criarMovimentacaoEntrada(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa);

    Movimentacao criarMovimentacaoSaida(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa);

    Movimentacao criarMovimentacaoSaida(ItemRequisicao itemRequisicao, String justificativa);

    Movimentacao criarMovimentacaoEntrada(ItemRequisicao itemRequisicao, String justificativa);

    Movimentacao criarMovimentacaoSaida(BaseItem itemEmprestimoAReceber, String justificativa);

    Movimentacao criarMovimentacaoEntrada(BaseItem itemEmprestimoAReceber, String justificativa);
}
