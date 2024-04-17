package com.example.meualmoxarifadobackend.controller.filter;

import com.example.meualmoxarifadobackend.domain.model.Material;
import org.springframework.data.jpa.domain.Specification;

public record MaterialSearchFilter(
        String label,
        String descricao,
        Long id_categoria
) {
    public Specification<Material> toSpec() {
        Specification<Material> spec = Specification.where(null);

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

    private Specification<Material> descricaoLike(String descricao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descricao")),
                        "%" + descricao.toLowerCase() + "%");
    }

    private Specification<Material> categoriaIdIs(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoria").get("id"), id);
    }
}
