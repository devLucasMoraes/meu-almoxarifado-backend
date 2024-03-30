package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Transportadora;
import jakarta.validation.constraints.NotBlank;

public record TransportadoraDTO(
        Long id,
        @NotBlank
        String cnpj,
        @NotBlank
        String razaoSocial,
        String nomeFantasia,
        String fone) {
    public Transportadora toModel() {
        Transportadora model = new Transportadora();
        model.setId(this.id);
        model.setNomeFantasia(this.nomeFantasia);
        model.setRazaoSocial(this.razaoSocial);
        model.setCnpj(this.cnpj);
        model.setFone(this.fone);
        return model;
    }
}
