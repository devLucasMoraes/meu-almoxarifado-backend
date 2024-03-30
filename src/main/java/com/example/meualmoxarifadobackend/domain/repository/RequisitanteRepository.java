package com.example.meualmoxarifadobackend.domain.repository;

import com.example.meualmoxarifadobackend.domain.model.Requisitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequisitanteRepository extends JpaRepository<Requisitante, Long>, JpaSpecificationExecutor<Requisitante> {

    boolean existsByNome(String nome);
}
