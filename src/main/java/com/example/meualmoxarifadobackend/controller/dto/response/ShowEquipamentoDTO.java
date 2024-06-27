package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.Equipamento;

import java.time.LocalDateTime;

public record ShowEquipamentoDTO(
        Long id,
        String nome,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ShowEquipamentoDTO(Equipamento equipamento) {
        this(equipamento.getId(), equipamento.getNome(), equipamento.getCreatedAt(), equipamento.getUpdatedAt());
    }
}
