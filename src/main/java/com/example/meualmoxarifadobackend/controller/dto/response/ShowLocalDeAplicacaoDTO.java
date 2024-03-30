package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.LocalDeAplicacao;

public record ShowLocalDeAplicacaoDTO(
        Long id,
        String nome) {
    public ShowLocalDeAplicacaoDTO(LocalDeAplicacao localDeAplicacao) {
        this(localDeAplicacao.getId(), localDeAplicacao.getNome());
    }
}
