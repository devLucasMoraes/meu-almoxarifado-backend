package com.example.meualmoxarifadobackend.domain.repository;


import com.example.meualmoxarifadobackend.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {

    boolean existsByNome(String nome);
}
