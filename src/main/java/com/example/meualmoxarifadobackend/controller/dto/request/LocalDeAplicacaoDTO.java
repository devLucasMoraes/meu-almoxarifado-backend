package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.LocalDeAplicacao;
import jakarta.validation.constraints.NotBlank;

public record LocalDeAplicacaoDTO(
        Long id,
        @NotBlank
        String nome) {
    public LocalDeAplicacao toModel() {
        LocalDeAplicacao model = new LocalDeAplicacao();
        model.setId(this.id);
        model.setNome(this.nome);
        return model;
    }

    public LocalDeAplicacao toNewModel() {
        LocalDeAplicacao model = new LocalDeAplicacao();
        model.setNome(this.nome);
        return model;
    }
}
