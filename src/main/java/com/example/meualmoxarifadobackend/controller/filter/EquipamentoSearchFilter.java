package com.example.meualmoxarifadobackend.controller.filter;

import com.example.meualmoxarifadobackend.domain.model.Equipamento;
import org.springframework.data.jpa.domain.Specification;

public record EquipamentoSearchFilter(
        String label,
        String nome
) {
    public Specification<Equipamento> toSpec() {
        Specification<Equipamento> spec = Specification.where(null);

        if (this.label != null) {
            spec = spec.and(nomeLike(this.label));
        }

        if(this.nome != null){
            spec = spec.and(nomeLike(this.nome));
        }

        return spec;
    }

    private Specification<Equipamento> nomeLike(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + nome.toLowerCase() + "%");
    }

}
