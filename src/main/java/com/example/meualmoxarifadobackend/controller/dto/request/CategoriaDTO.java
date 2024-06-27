package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Categoria;
import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,
        @NotBlank
        String nome
) {
    public Categoria toModel() {
        Categoria model = new Categoria();
        model.setId(this.id);
        model.setNome(this.nome);
        return model;
    }
}
