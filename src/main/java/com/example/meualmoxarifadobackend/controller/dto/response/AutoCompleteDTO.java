package com.example.meualmoxarifadobackend.controller.dto.response;


import com.example.meualmoxarifadobackend.domain.model.*;

public record AutoCompleteDTO( Long id, String label ) {
    public AutoCompleteDTO(Material model) {
        this(
                model.getId(),
                model.getDescricao()
        );
    }

    public AutoCompleteDTO(Categoria model) {
        this(
                model.getId(),
                model.getNome()
        );
    }

    public AutoCompleteDTO(Fornecedora model) {
        this(
                model.getId(),
                model.getNomeFantasia()
        );
    }

    public AutoCompleteDTO(Transportadora model) {
        this(
                model.getId(),
                model.getNomeFantasia()
        );
    }

    public AutoCompleteDTO(LocalDeAplicacao model) {
        this(
                model.getId(),
                model.getNome()
        );
    }

    public AutoCompleteDTO(Requisitante model) {
        this(
                model.getId(),
                model.getNome()
        );
    }
}
