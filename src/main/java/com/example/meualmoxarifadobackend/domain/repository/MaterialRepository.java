package com.example.meualmoxarifadobackend.domain.repository;


import com.example.meualmoxarifadobackend.domain.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

    boolean existsByDescricao(String descricao);
}
