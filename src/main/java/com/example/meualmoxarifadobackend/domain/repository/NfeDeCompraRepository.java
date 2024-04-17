package com.example.meualmoxarifadobackend.domain.repository;


import com.example.meualmoxarifadobackend.domain.model.NfeDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NfeDeCompraRepository extends JpaRepository<NfeDeCompra, Long> {
}
