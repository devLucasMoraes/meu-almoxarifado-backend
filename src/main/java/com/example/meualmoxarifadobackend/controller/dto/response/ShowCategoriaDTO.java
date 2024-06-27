package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.Categoria;

public record ShowCategoriaDTO(
        Long id,

        String nome
) {
    public ShowCategoriaDTO(Categoria categoria) {
        this(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
