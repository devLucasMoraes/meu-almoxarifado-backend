package com.example.meualmoxarifadobackend.controller.filter;

import com.example.meualmoxarifadobackend.domain.model.VinculoMaterialFornecedora;
import org.springframework.data.jpa.domain.Specification;

public record VinculoMaterialFornecedoraSearchFilter(

        String id_fornecedora,

        String id_material,

        String referencia_fornecedora,

        String descricao_fornecedora
) {
    public Specification<VinculoMaterialFornecedora> toSpec() {
        Specification<VinculoMaterialFornecedora> spec = Specification.where(null);

        if (this.id_fornecedora != null) {
            spec = spec.and(fornecedoraIdIs(this.id_fornecedora));
        }

        if (this.id_material != null) {
            spec = spec.and(materialIdIs(this.id_material));
        }

        if (this.referencia_fornecedora != null) {
            spec = spec.and(referenciafornecedoraIs(this.referencia_fornecedora));
        }

        if (this.descricao_fornecedora != null) {
            spec = spec.and(descricaoFornecedoraLike(this.descricao_fornecedora));
        }

        return spec;
    }

    private Specification<VinculoMaterialFornecedora> descricaoFornecedoraLike(String descricao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descricaoFornecedora")),
                        "%" + descricao.toLowerCase() + "%");
    }

    private Specification<VinculoMaterialFornecedora> referenciafornecedoraIs(String referencia) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("referenciaFornecedora"), referencia);
    }

    private Specification<VinculoMaterialFornecedora> materialIdIs(String id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("material").get("id"), id);
    }

    private Specification<VinculoMaterialFornecedora> fornecedoraIdIs(String id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fornecedora").get("id"), id);
    }
}
