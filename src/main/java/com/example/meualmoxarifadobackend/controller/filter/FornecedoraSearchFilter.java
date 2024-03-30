package com.example.meualmoxarifadobackend.controller.filter;

import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import org.springframework.data.jpa.domain.Specification;

public record FornecedoraSearchFilter(
        String label,
        String cnpj,
        String razao_social,
        String nome_fantasia,
        String fone
) {
    public Specification<Fornecedora> toSpec() {
        Specification<Fornecedora> spec = Specification.where(null);

        if (this.label != null) {
            spec = spec.and(nomeFantasiaLike(this.label));
        }

        if (this.cnpj != null) {
            spec = spec.and(cnpjIs(this.cnpj));
        }

        if (this.razao_social != null) {
            spec = spec.and(razaoSocialLike(this.razao_social));
        }

        if (this.nome_fantasia != null) {
            spec = spec.and(nomeFantasiaLike(this.nome_fantasia));
        }

        if (this.fone != null) {
            spec = spec.and(foneLike(this.fone));
        }

        return spec;
    }

    private Specification<Fornecedora> razaoSocialLike(String razaoSocial) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("razaoSocial")),
                        "%" + razaoSocial.toLowerCase() + "%");
    }

    private Specification<Fornecedora> nomeFantasiaLike(String nomeFantasia) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nomeFantasia")),
                        "%" + nomeFantasia.toLowerCase() + "%");
    }

    private Specification<Fornecedora> foneLike(String fone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("fone")),
                        "%" + fone.toLowerCase() + "%");
    }

    private Specification<Fornecedora> cnpjIs(String cnpj) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cnpj"), cnpj);
    }
}
