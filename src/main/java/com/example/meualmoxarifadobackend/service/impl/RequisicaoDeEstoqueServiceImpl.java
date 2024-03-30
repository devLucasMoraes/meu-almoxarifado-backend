package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.ItemRequisicao;
import com.example.meualmoxarifadobackend.domain.model.Movimentacao;
import com.example.meualmoxarifadobackend.domain.model.RequisicaoDeEstoque;
import com.example.meualmoxarifadobackend.domain.repository.RequisicaoDeEstoqueRepository;
import com.example.meualmoxarifadobackend.service.*;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RequisicaoDeEstoqueServiceImpl implements RequisicaoDeEstoqueService {

    private final RequisicaoDeEstoqueRepository requisicaoDeEstoqueRepository;

    private final LocalDeAplicacaoService localDeAplicacaoService;

    private final RequisitanteService requisitanteService;

    private final MaterialService materialService;

    private final MovimentacaoService movimentacaoService;

    private final MovimentacaoFactory movimentacaoFactory;

    public RequisicaoDeEstoqueServiceImpl(
            RequisicaoDeEstoqueRepository requisicaoDeEstoqueRepository,
            LocalDeAplicacaoService localDeAplicacaoService,
            RequisitanteService requisitanteService,
            MaterialService materialService,
            MovimentacaoService movimentacaoService,
            MovimentacaoFactory movimentacaoFactory
    ) {
        this.requisicaoDeEstoqueRepository = requisicaoDeEstoqueRepository;
        this.localDeAplicacaoService = localDeAplicacaoService;
        this.requisitanteService = requisitanteService;
        this.materialService = materialService;
        this.movimentacaoService = movimentacaoService;
        this.movimentacaoFactory = movimentacaoFactory;
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoDeEstoque> findAll(Pageable pageable) {
        return this.requisicaoDeEstoqueRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public RequisicaoDeEstoque findById(Long id) {
        return this.requisicaoDeEstoqueRepository.findById(id).orElseThrow(() -> new NotFoundException("Requisiçao"));
    }

    @Transactional
    public RequisicaoDeEstoque create(RequisicaoDeEstoque requisicaoToCreate) {

        if (!this.requisitanteService.existsById(requisicaoToCreate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.localDeAplicacaoService.existsById(requisicaoToCreate.getLocalDeAplicacao().getId())) {
            throw new NotFoundException("Local de aplicação");
        }

        requisicaoToCreate.getItens().forEach(itemRequisicao -> {
            if (!this.materialService.existsById(itemRequisicao.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemRequisicao.setRequisicaoDeEstoque(requisicaoToCreate);


            Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemRequisicao,
                    "Requisição de estoque. Local id: %s , Requisitante id: %s".formatted(requisicaoToCreate.getLocalDeAplicacao().getId(), requisicaoToCreate.getRequisitante().getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);

            itemRequisicao.setValorUnitario(saida.getValorUnt());

        });

        BigDecimal valorTotalItens = requisicaoToCreate.getItens().stream().map(ItemRequisicao::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        requisicaoToCreate.setValorTotal(valorTotalItens);

        return this.requisicaoDeEstoqueRepository.save(requisicaoToCreate);
    }

    @Transactional
    public RequisicaoDeEstoque update(Long id, RequisicaoDeEstoque requisicaoToUpdate) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);

        if (!dbRequisicaoDeEstoque.getId().equals(requisicaoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (!this.requisitanteService.existsById(requisicaoToUpdate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.localDeAplicacaoService.existsById(requisicaoToUpdate.getLocalDeAplicacao().getId())) {
            throw new NotFoundException("Local de aplicação");
        }

        dbRequisicaoDeEstoque.getItens().forEach(itemRequisicao -> {
            Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(itemRequisicao,
                    "Alteração de Requisiçao id : %s".formatted(dbRequisicaoDeEstoque.getId()));
            this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);
        });

        requisicaoToUpdate.getItens().forEach(itemRequisicao -> {
            if (!this.materialService.existsById(itemRequisicao.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemRequisicao.setRequisicaoDeEstoque(requisicaoToUpdate);

            Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemRequisicao,
                    "Alteração de Requisiçao id : %s".formatted(dbRequisicaoDeEstoque.getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);

            itemRequisicao.setValorUnitario(saida.getValorUnt());
        });

        BigDecimal valorTotalItens = requisicaoToUpdate.getItens().stream().map(ItemRequisicao::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        dbRequisicaoDeEstoque.setObs(requisicaoToUpdate.getObs());
        dbRequisicaoDeEstoque.setValorTotal(valorTotalItens);
        dbRequisicaoDeEstoque.setDataRequisicao(requisicaoToUpdate.getDataRequisicao());
        dbRequisicaoDeEstoque.setLocalDeAplicacao(requisicaoToUpdate.getLocalDeAplicacao());
        dbRequisicaoDeEstoque.setRequisitante(requisicaoToUpdate.getRequisitante());
        dbRequisicaoDeEstoque.setOrdemProducao(requisicaoToUpdate.getOrdemProducao());
        dbRequisicaoDeEstoque.getItens().clear();
        dbRequisicaoDeEstoque.getItens().addAll(requisicaoToUpdate.getItens());

        return this.requisicaoDeEstoqueRepository.save(dbRequisicaoDeEstoque);
    }

    @Transactional
    public void delete(Long id) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);

        dbRequisicaoDeEstoque.getItens().forEach(itemRequisicao -> {
            Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(itemRequisicao,
                    "Cancelamento de Requisiçao id : %s".formatted(dbRequisicaoDeEstoque.getId()));
            this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);
        });

        this.requisicaoDeEstoqueRepository.delete(dbRequisicaoDeEstoque);
    }

}
