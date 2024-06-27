package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.Categoria;

import java.time.LocalDateTime;

public record ShowCategoriaDTO(
        Long id,
        String nome,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ShowCategoriaDTO(Categoria categoria) {
        this(
                categoria.getId(),
                categoria.getNome(),
                categoria.getCreatedAt(),
                categoria.getUpdatedAt()
        );
    }
}
