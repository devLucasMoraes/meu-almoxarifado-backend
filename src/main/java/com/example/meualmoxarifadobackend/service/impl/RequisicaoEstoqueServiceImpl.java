package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoqueItem;
import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoque;
import com.example.meualmoxarifadobackend.domain.repository.RequisicaoEstoqueRepository;
import com.example.meualmoxarifadobackend.service.*;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RequisicaoEstoqueServiceImpl implements RequisicaoEstoqueService {

    private final RequisicaoEstoqueRepository requisicaoEstoqueRepository;

    private final EquipamentoService equipamentoService;

    private final RequisitanteService requisitanteService;

    private final InsumoService insumoService;


    public RequisicaoEstoqueServiceImpl(
            RequisicaoEstoqueRepository requisicaoEstoqueRepository,
            EquipamentoService equipamentoService,
            RequisitanteService requisitanteService,
            InsumoService insumoService
    ) {
        this.requisicaoEstoqueRepository = requisicaoEstoqueRepository;
        this.equipamentoService = equipamentoService;
        this.requisitanteService = requisitanteService;
        this.insumoService = insumoService;
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoEstoque> findAll(Pageable pageable) {
        return this.requisicaoEstoqueRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public RequisicaoEstoque findById(Long id) {
        return this.requisicaoEstoqueRepository.findById(id).orElseThrow(() -> new NotFoundException("Requisiçao"));
    }

    @Transactional
    public RequisicaoEstoque create(RequisicaoEstoque requisicaoToCreate) {

        if (!this.requisitanteService.existsById(requisicaoToCreate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.equipamentoService.existsById(requisicaoToCreate.getEquipamento().getId())) {
            throw new NotFoundException("Equipamento");
        }

        requisicaoToCreate.getItens().forEach(itemRequisicao -> {
            if (!this.insumoService.existsById(itemRequisicao.getInsumo().getId())) {
                throw new NotFoundException("Insumo");
            }

            itemRequisicao.setRequisicaoEstoque(requisicaoToCreate);

        });

        BigDecimal valorTotalItens = requisicaoToCreate.getItens().stream().map(RequisicaoEstoqueItem::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        requisicaoToCreate.setValorTotal(valorTotalItens);

        return this.requisicaoEstoqueRepository.save(requisicaoToCreate);
    }

    @Transactional
    public RequisicaoEstoque update(Long id, RequisicaoEstoque requisicaoToUpdate) {
        RequisicaoEstoque dbRequisicaoEstoque = this.findById(id);

        if (!dbRequisicaoEstoque.getId().equals(requisicaoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (!this.requisitanteService.existsById(requisicaoToUpdate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.equipamentoService.existsById(requisicaoToUpdate.getEquipamento().getId())) {
            throw new NotFoundException("Equipamento");
        }

        requisicaoToUpdate.getItens().forEach(itemRequisicao -> {
            if (!this.insumoService.existsById(itemRequisicao.getInsumo().getId())) {
                throw new NotFoundException("Material");
            }

            itemRequisicao.setRequisicaoEstoque(requisicaoToUpdate);

        });

        BigDecimal valorTotalItens = requisicaoToUpdate.getItens().stream().map(RequisicaoEstoqueItem::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        dbRequisicaoEstoque.setObs(requisicaoToUpdate.getObs());
        dbRequisicaoEstoque.setValorTotal(valorTotalItens);
        dbRequisicaoEstoque.setDataRequisicao(requisicaoToUpdate.getDataRequisicao());
        dbRequisicaoEstoque.setEquipamento(requisicaoToUpdate.getEquipamento());
        dbRequisicaoEstoque.setRequisitante(requisicaoToUpdate.getRequisitante());
        dbRequisicaoEstoque.setOrdemProducao(requisicaoToUpdate.getOrdemProducao());
        dbRequisicaoEstoque.getItens().clear();
        dbRequisicaoEstoque.getItens().addAll(requisicaoToUpdate.getItens());

        return this.requisicaoEstoqueRepository.save(dbRequisicaoEstoque);
    }

    @Transactional
    public void delete(Long id) {

        RequisicaoEstoque dbRequisicaoEstoque = this.findById(id);

        this.requisicaoEstoqueRepository.delete(dbRequisicaoEstoque);

    }

}
