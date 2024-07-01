package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoque;
import com.example.meualmoxarifadobackend.domain.model.RequisicaoEstoqueItem;
import com.example.meualmoxarifadobackend.domain.repository.RequisicaoEstoqueRepository;
import com.example.meualmoxarifadobackend.service.EquipamentoService;
import com.example.meualmoxarifadobackend.service.InsumoService;
import com.example.meualmoxarifadobackend.service.RequisicaoEstoqueService;
import com.example.meualmoxarifadobackend.service.RequisitanteService;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

        requisicaoToCreate.getItens().forEach(itemToCreate -> {
            if (!this.insumoService.existsById(itemToCreate.getInsumo().getId())) {
                throw new NotFoundException("Insumo");
            }

            var insumo = this.insumoService.findById(itemToCreate.getInsumo().getId());

            if (!itemToCreate.getUnidade().equals(insumo.getUndEstoque())) {
                throw new BusinessException("Unidade de Consumo tem que ser igual unidade de estoque");
            }

            itemToCreate.setRequisicaoEstoque(requisicaoToCreate);
            itemToCreate.setValorUnitario(insumo.getValorUntMed());

            this.insumoService.incrementTotalSaidas(itemToCreate.getQuantidade(), insumo);

        });

        BigDecimal valorTotalItens = requisicaoToCreate.getItens().stream()
                .map(RequisicaoEstoqueItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        requisicaoToCreate.setValorTotal(valorTotalItens);
        requisicaoToCreate.setCreatedAt(LocalDateTime.now());
        requisicaoToCreate.setUpdatedAt(LocalDateTime.now());

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

        requisicaoToUpdate.getItens().forEach(itemToUpdate -> {
            if (!this.insumoService.existsById(itemToUpdate.getInsumo().getId())) {
                throw new NotFoundException("Insumo");
            }

            var insumo = this.insumoService.findById(itemToUpdate.getInsumo().getId());

            if (!itemToUpdate.getUnidade().equals(insumo.getUndEstoque())) {
                throw new BusinessException("Unidade de consumo tem que ser igual unidade de estoque.");
            }

            var oldQuantidade = dbRequisicaoEstoque.getItens().stream()
                    .filter(requisicaoEstoqueItem -> requisicaoEstoqueItem.getId().equals(itemToUpdate.getId()))
                    .findFirst()
                    .map(RequisicaoEstoqueItem::getQuantidade)
                    .orElse(BigDecimal.ZERO);

            var quamtidadeToUpdate = itemToUpdate.getQuantidade().subtract(oldQuantidade);


            itemToUpdate.setRequisicaoEstoque(requisicaoToUpdate);
            itemToUpdate.setValorUnitario(insumo.getValorUntMed());

            this.insumoService.incrementTotalSaidas(quamtidadeToUpdate, insumo);


        });

        BigDecimal valorTotalItens = requisicaoToUpdate.getItens().stream()
                .map(RequisicaoEstoqueItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dbRequisicaoEstoque.setObs(requisicaoToUpdate.getObs());
        dbRequisicaoEstoque.setValorTotal(valorTotalItens);
        dbRequisicaoEstoque.setDataRequisicao(requisicaoToUpdate.getDataRequisicao());
        dbRequisicaoEstoque.setEquipamento(requisicaoToUpdate.getEquipamento());
        dbRequisicaoEstoque.setRequisitante(requisicaoToUpdate.getRequisitante());
        dbRequisicaoEstoque.setOrdemProducao(requisicaoToUpdate.getOrdemProducao());
        dbRequisicaoEstoque.getItens().clear();
        dbRequisicaoEstoque.getItens().addAll(requisicaoToUpdate.getItens());
        dbRequisicaoEstoque.setUpdatedAt(LocalDateTime.now());

        return this.requisicaoEstoqueRepository.save(dbRequisicaoEstoque);
    }

    @Transactional
    public void delete(Long id) {

        RequisicaoEstoque dbRequisicaoEstoque = this.findById(id);

        dbRequisicaoEstoque.getItens().forEach(itemToDelete -> {

            var insumo = this.insumoService.findById(itemToDelete.getInsumo().getId());

            this.insumoService.decrementTotalSaidas(itemToDelete.getQuantidade(), insumo);

        });

        this.requisicaoEstoqueRepository.delete(dbRequisicaoEstoque);

    }

}
