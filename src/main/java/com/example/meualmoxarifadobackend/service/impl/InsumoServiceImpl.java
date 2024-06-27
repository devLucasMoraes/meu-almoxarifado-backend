package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.domain.model.Insumo;
import com.example.meualmoxarifadobackend.domain.repository.InsumoRepository;
import com.example.meualmoxarifadobackend.service.CategoriaService;
import com.example.meualmoxarifadobackend.service.InsumoService;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;

    private final CategoriaService categoriaService;

    public InsumoServiceImpl(InsumoRepository insumoRepository, CategoriaService categoriaService) {
        this.insumoRepository = insumoRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional(readOnly = true)
    public Page<Insumo> findAll(Pageable pageable) {
        return this.insumoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Insumo> dynamicFindAll(Specification<Insumo> specification, Pageable pageable) {

        return this.insumoRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Insumo findById(Long id) {
        return this.insumoRepository.findById(id).orElseThrow(() -> new NotFoundException("Insumo"));
    }

    @Transactional
    public Insumo create(Insumo insumoToCreate) {

        if (!categoriaService.existsById(insumoToCreate.getCategoria().getId())) {
            throw new NotFoundException("Categoria");
        }

        if (insumoRepository.existsByDescricao(insumoToCreate.getDescricao())) {
            throw new BusinessException("Insumo com mesma descrição já cadastrada");
        }


        return this.insumoRepository.save(insumoToCreate);
    }

    @Transactional
    public Insumo update(Long id, Insumo insumoToUpdate) {
        Insumo dbInsumo = this.findById(id);

        if (!dbInsumo.getId().equals(insumoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbInsumo.setDescricao(insumoToUpdate.getDescricao());
        dbInsumo.setValorUntMedAuto(insumoToUpdate.getValorUntMedAuto());
        if (!insumoToUpdate.getValorUntMedAuto()) {
            dbInsumo.setValorUntMed(insumoToUpdate.getValorUntMed());
        }
        dbInsumo.setCategoria(insumoToUpdate.getCategoria());

        return this.insumoRepository.save(dbInsumo);
    }

    @Transactional
    public void acertarEstoque(AcertoEstoqueDTO acerto) {
        Insumo dbInsumo = this.findById(acerto.idMaterial());


    }

    @Transactional
    public void delete(Long id) {
        Insumo dbInsumo = this.findById(id);
        this.insumoRepository.delete(dbInsumo);
    }

    public Boolean existsById(Long id) {
        return this.insumoRepository.existsById(id);
    }
}
