package com.example.meualmoxarifadobackend.controller.dto.response;

import com.example.meualmoxarifadobackend.domain.model.Requisitante;

import java.time.LocalDateTime;

public record ShowRequisitanteDTO(
        Long id,
        String nome,
        String fone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ShowRequisitanteDTO(Requisitante requisitante) {
        this(requisitante.getId(), requisitante.getNome(), requisitante.getFone(), requisitante.getCreatedAt(), requisitante.getUpdatedAt());
    }
}
