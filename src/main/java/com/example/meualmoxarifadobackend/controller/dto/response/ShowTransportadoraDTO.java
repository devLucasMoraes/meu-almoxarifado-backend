package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.Transportadora;

public record ShowTransportadoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone
) {
    public ShowTransportadoraDTO(Transportadora transportadora) {
        this(transportadora.getId(), transportadora.getCnpj(), transportadora.getRazaoSocial(), transportadora.getNomeFantasia(), transportadora.getFone());
    }
}
