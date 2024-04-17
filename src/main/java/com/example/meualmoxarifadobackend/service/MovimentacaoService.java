package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.Movimentacao;

public interface MovimentacaoService {

    void registrarEntradaAoEstoqueFisico(Movimentacao entrada);

    void registrarSaidaDoEstoqueFisico(Movimentacao saida);

}
