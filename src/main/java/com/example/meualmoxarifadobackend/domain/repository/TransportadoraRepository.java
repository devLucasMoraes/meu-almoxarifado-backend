package com.example.meualmoxarifadobackend.domain.repository;

import com.example.meualmoxarifadobackend.domain.model.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora, Long>, JpaSpecificationExecutor<Transportadora> {

    boolean existsByCnpj(String cnpj);

    Optional<Transportadora> getReferenceByCnpj(String cnpj);
}
