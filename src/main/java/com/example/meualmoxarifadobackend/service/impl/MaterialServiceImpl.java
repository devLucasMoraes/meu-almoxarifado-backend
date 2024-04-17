package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.domain.model.Material;
import com.example.meualmoxarifadobackend.domain.repository.MaterialRepository;
import com.example.meualmoxarifadobackend.service.CategoriaService;
import com.example.meualmoxarifadobackend.service.FornecedoraService;
import com.example.meualmoxarifadobackend.service.MaterialService;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    private final CategoriaService categoriaService;

    private final FornecedoraService fornecedoraService;

    public MaterialServiceImpl(MaterialRepository materialRepository, CategoriaService categoriaService, FornecedoraService fornecedoraService) {
        this.materialRepository = materialRepository;
        this.categoriaService = categoriaService;
        this.fornecedoraService = fornecedoraService;
    }

    @Transactional(readOnly = true)
    public Page<Material> findAll(Pageable pageable) {
        return this.materialRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Material> dynamicFindAll(Specification<Material> specification, Pageable pageable) {

        return this.materialRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Material findById(Long id) {
        return this.materialRepository.findById(id).orElseThrow(() -> new NotFoundException("Material"));
    }

    @Transactional
    public Material create(Material materialToCreate) {

        if (!categoriaService.existsById(materialToCreate.getCategoria().getId())) {
            throw new NotFoundException("Categoria");
        }

        if (materialRepository.existsByDescricao(materialToCreate.getDescricao())) {
            throw new BusinessException("Material com mesma descrição já cadastrada");
        }

        materialToCreate.getFornecedorasVinculadas().forEach(vinculoMaterialComFornecedora -> {
            if (!this.fornecedoraService.existsById(vinculoMaterialComFornecedora.getFornecedora().getId())) {
                throw new NotFoundException("Fornecedora");
            }
            vinculoMaterialComFornecedora.setMaterial(materialToCreate);

            vinculoMaterialComFornecedora.getConversaoDeCompras().forEach(conversaoDeCompra -> conversaoDeCompra.setVinculoComFornecedoras(vinculoMaterialComFornecedora));
        });

        return this.materialRepository.save(materialToCreate);
    }

    @Transactional
    public Material update(Long id, Material materialToUpdate) {
        Material dbMaterial = this.findById(id);

        if (!dbMaterial.getId().equals(materialToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        materialToUpdate.getFornecedorasVinculadas().forEach(vinculoMaterialComFornecedora -> {
            if (!this.fornecedoraService.existsById(vinculoMaterialComFornecedora.getFornecedora().getId())) {
                throw new NotFoundException("Fornecedora");
            }
            vinculoMaterialComFornecedora.setMaterial(materialToUpdate);

            vinculoMaterialComFornecedora.getConversaoDeCompras().forEach(conversaoDeCompra -> conversaoDeCompra.setVinculoComFornecedoras(vinculoMaterialComFornecedora));
        });

        dbMaterial.setDescricao(materialToUpdate.getDescricao());
        dbMaterial.setValorUntMedAuto(materialToUpdate.getValorUntMedAuto());
        if (!materialToUpdate.getValorUntMedAuto()) {
            dbMaterial.setValorUntMed(materialToUpdate.getValorUntMed());
        }
        dbMaterial.getFornecedorasVinculadas().clear();
        dbMaterial.getFornecedorasVinculadas().addAll(materialToUpdate.getFornecedorasVinculadas());
        dbMaterial.setCategoria(materialToUpdate.getCategoria());

        return this.materialRepository.save(dbMaterial);
    }

    @Transactional
    public void acertarEstoque(AcertoEstoqueDTO acerto) {
        Material dbMaterial = this.findById(acerto.idMaterial());

        dbMaterial.setQtdEmEstoqueFisico(acerto.quantidade());

    }

    @Transactional
    public void delete(Long id) {
        Material dbMaterial = this.findById(id);
        this.materialRepository.delete(dbMaterial);
    }

    public Boolean existsById(Long id) {
        return this.materialRepository.existsById(id);
    }
}
