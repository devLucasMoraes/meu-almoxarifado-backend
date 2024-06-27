package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.Equipamento;

public record ShowEquipamentoDTO(
        Long id,
        String nome) {
    public ShowEquipamentoDTO(Equipamento equipamento) {
        this(equipamento.getId(), equipamento.getNome());
    }
}
