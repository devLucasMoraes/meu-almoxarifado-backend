package com.example.meualmoxarifadobackend.domain.repository;

import com.example.meualmoxarifadobackend.domain.model.ConversaoDeConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversaoDeConsumoRepository extends JpaRepository<ConversaoDeConsumo, Long> {
}
