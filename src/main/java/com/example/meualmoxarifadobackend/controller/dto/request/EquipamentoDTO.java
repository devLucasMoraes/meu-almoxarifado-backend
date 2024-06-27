package com.example.meualmoxarifadobackend.controller.dto.request;

import com.example.meualmoxarifadobackend.domain.model.Equipamento;
import jakarta.validation.constraints.NotBlank;

public record EquipamentoDTO(
        Long id,
        @NotBlank String nome
) {
    public Equipamento toModel() {
        Equipamento model = new Equipamento();
        model.setId(this.id);
        model.setNome(this.nome);
        return model;
    }

    public Equipamento toNewModel() {
        Equipamento model = new Equipamento();
        model.setNome(this.nome);
        return model;
    }
}
