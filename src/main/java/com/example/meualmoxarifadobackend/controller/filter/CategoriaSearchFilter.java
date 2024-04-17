package com.example.meualmoxarifadobackend.controller.filter;

import com.example.meualmoxarifadobackend.domain.model.Categoria;
import org.springframework.data.jpa.domain.Specification;

public record CategoriaSearchFilter(
        String label,
        String nome
) {
    public Specification<Categoria> toSpec() {
        Specification<Categoria> spec = Specification.where(null);

        if (this.label != null) {
            spec = spec.and(nomeLike(this.label));
        }

        if(this.nome != null){
            spec = spec.and(nomeLike(this.nome));
        }

        return spec;
    }

    private Specification<Categoria> nomeLike(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + nome.toLowerCase() + "%");
    }

}
