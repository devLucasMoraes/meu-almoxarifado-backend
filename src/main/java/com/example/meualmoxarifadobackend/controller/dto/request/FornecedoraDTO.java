package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import jakarta.validation.constraints.NotBlank;

public record FornecedoraDTO(
        Long id,
        @NotBlank
        String cnpj,
        @NotBlank
        String razaoSocial,
        String nomeFantasia,
        String fone) {
    public Fornecedora toModel() {
        Fornecedora model = new Fornecedora();
        model.setId(this.id);
        model.setCnpj(this.cnpj);
        model.setRazaoSocial(this.razaoSocial);
        model.setNomeFantasia(this.nomeFantasia);
        model.setFone(this.fone);
        return model;
    }
}
