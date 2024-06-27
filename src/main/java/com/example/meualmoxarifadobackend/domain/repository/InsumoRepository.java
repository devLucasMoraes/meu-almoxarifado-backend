package com.example.meualmoxarifadobackend.domain.repository;


import com.example.meualmoxarifadobackend.domain.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long>, JpaSpecificationExecutor<Insumo> {

    boolean existsByDescricao(String descricao);
}
