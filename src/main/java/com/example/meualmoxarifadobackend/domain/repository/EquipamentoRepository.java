package com.example.meualmoxarifadobackend.domain.repository;


import com.example.meualmoxarifadobackend.domain.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>, JpaSpecificationExecutor<Equipamento> {
    boolean existsByNome(String nome);
}
