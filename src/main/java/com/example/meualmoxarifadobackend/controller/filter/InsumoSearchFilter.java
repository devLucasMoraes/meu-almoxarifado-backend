package com.example.meualmoxarifadobackend.controller.filter;

import com.example.meualmoxarifadobackend.domain.model.Insumo;
import org.springframework.data.jpa.domain.Specification;

public record InsumoSearchFilter(
        String label,
        String descricao,
        Long id_categoria
) {
    public Specification<Insumo> toSpec() {
        Specification<Insumo> spec = Specification.where(null);

        if (this.label != null) {
            spec = spec.and(descricaoLike(this.label));
        }

        if(this.descricao != null){
            spec = spec.and(descricaoLike(this.descricao));
        }

        if(this.id_categoria != null){
            spec = spec.and(categoriaIdIs(this.id_categoria));
        }

        return spec;
    }

    private Specification<Insumo> descricaoLike(String descricao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descricao")),
                        "%" + descricao.toLowerCase() + "%");
    }

    private Specification<Insumo> categoriaIdIs(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoria").get("id"), id);
    }
}
