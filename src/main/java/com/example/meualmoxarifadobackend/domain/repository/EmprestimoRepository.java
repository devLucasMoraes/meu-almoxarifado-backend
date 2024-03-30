package com.example.meualmoxarifadobackend.domain.repository;

import com.example.meualmoxarifadobackend.domain.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
