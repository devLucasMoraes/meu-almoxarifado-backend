package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.*;
import com.example.meualmoxarifadobackend.domain.repository.EmprestimoRepository;
import com.example.meualmoxarifadobackend.service.*;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    private final FornecedoraService fornecedoraService;

    private final MaterialService materialService;

    private final MovimentacaoService movimentacaoService;

    private final MovimentacaoFactory movimentacaoFactory;

    public EmprestimoServiceImpl(EmprestimoRepository emprestimoRepository, FornecedoraService fornecedoraService, MaterialService materialService, MovimentacaoService movimentacaoService, MovimentacaoFactory movimentacaoFactory) {
        this.emprestimoRepository = emprestimoRepository;
        this.fornecedoraService = fornecedoraService;
        this.materialService = materialService;
        this.movimentacaoService = movimentacaoService;
        this.movimentacaoFactory = movimentacaoFactory;
    }

    @Transactional(readOnly = true)
    public Page<Emprestimo> findAll(Pageable pageable) {
        return this.emprestimoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Emprestimo findById(Long id) {
        return this.emprestimoRepository.findById(id).orElseThrow(() -> new NotFoundException("Empréstimo"));
    }

    @Transactional
    public Emprestimo create(Emprestimo emprestimoToCreate) {
        if (!this.fornecedoraService.existsById(emprestimoToCreate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        emprestimoToCreate.getItensAReceber().forEach(item -> {
            if (!this.materialService.existsById(item.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            item.setEmprestimo(emprestimoToCreate);

            if (emprestimoToCreate.getAtribuirAoEstoqueFisico()) {
                emprestimoToCreate.setTipo(Tipo.ENTRADA);
                Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(item,
                        "Entrada de material referente a empréstimo da Fornecedora id: %s".formatted(emprestimoToCreate.getFornecedora().getId()));
                this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);

                //item.setValorUnitario(entrada.getValorUnt());
            }

        });

        if (emprestimoToCreate.getSituacao().equals(Situacao.BAIXADO)) {
            emprestimoToCreate.getItensAPagar().forEach(itemEmprestimoAPagar -> {
                if (!this.materialService.existsById(itemEmprestimoAPagar.getMaterial().getId())) {
                    throw new NotFoundException("Material");
                }
                itemEmprestimoAPagar.setEmprestimo(emprestimoToCreate);

                Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemEmprestimoAPagar,
                        "Saída de material referente a empréstimo à: Fornecedora id: %s".formatted(emprestimoToCreate.getFornecedora().getId()));
                this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);

                itemEmprestimoAPagar.setValorUnitario(saida.getValorUnt());

            });
        }


        BigDecimal valorTotalItens = emprestimoToCreate.getItensAReceber().stream().map(ItemEmprestimoAReceber::getValorUnitario).reduce(BigDecimal.ZERO, BigDecimal::add);
        emprestimoToCreate.setValorTotal(valorTotalItens);

        return this.emprestimoRepository.save(emprestimoToCreate);
    }

    public Emprestimo update(Long id, Emprestimo emprestimoToUpdate) {
        Emprestimo dbEmprestimo = this.findById(id);

        if (!dbEmprestimo.getId().equals(emprestimoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (dbEmprestimo.getSituacao().equals(Situacao.BAIXADO)) {
            throw new BusinessException("Não é permitido alterações em emprestimos já baixados");
        }

        if (!this.fornecedoraService.existsById(emprestimoToUpdate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if (emprestimoToUpdate.getAtribuirAoEstoqueFisico()) {
            if(dbEmprestimo.getAtribuirAoEstoqueFisico()) {
                dbEmprestimo.getItensAReceber().forEach(itemEmprestimoAReceber -> {
                    Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemEmprestimoAReceber,
                            "Alteração de emprestimo id : %s".formatted(dbEmprestimo.getId()));
                    this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
                });
            }

            emprestimoToUpdate.getItensAReceber().forEach(itemEmprestimoAReceber -> {
                if (!this.materialService.existsById(itemEmprestimoAReceber.getMaterial().getId())) {
                    throw new NotFoundException("Material");
                }

                itemEmprestimoAReceber.setEmprestimo(emprestimoToUpdate);

                Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(itemEmprestimoAReceber,
                        "Alteração de emprestimo id : %s".formatted(dbEmprestimo.getId()));

                this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);

            });
        }

        if (!emprestimoToUpdate.getAtribuirAoEstoqueFisico()) {
            if(dbEmprestimo.getAtribuirAoEstoqueFisico()) {
                dbEmprestimo.getItensAReceber().forEach(itemEmprestimoAReceber -> {
                    Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemEmprestimoAReceber,
                            "Alteração de emprestimo id : %s".formatted(dbEmprestimo.getId()));
                    this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
                });
            }

            emprestimoToUpdate.getItensAReceber().forEach(itemEmprestimoAReceber -> {
                if (!this.materialService.existsById(itemEmprestimoAReceber.getMaterial().getId())) {
                    throw new NotFoundException("Material");
                }

                itemEmprestimoAReceber.setEmprestimo(emprestimoToUpdate);
            });
        }

        if (emprestimoToUpdate.getSituacao().equals(Situacao.BAIXADO)) {
            emprestimoToUpdate.getItensAPagar().forEach(itemEmprestimoAPagar -> {
                if (!this.materialService.existsById(itemEmprestimoAPagar.getMaterial().getId())) {
                    throw new NotFoundException("Material");
                }
                itemEmprestimoAPagar.setEmprestimo(emprestimoToUpdate);

                Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemEmprestimoAPagar,
                        "Saída de material referente a empréstimo à: Fornecedora id: %s".formatted(emprestimoToUpdate.getFornecedora().getId()));
                this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);

                itemEmprestimoAPagar.setValorUnitario(saida.getValorUnt());

            });

            dbEmprestimo.getItensAPagar().clear();
            dbEmprestimo.getItensAPagar().addAll(emprestimoToUpdate.getItensAPagar());
        }

        dbEmprestimo.setFornecedora(emprestimoToUpdate.getFornecedora());
        dbEmprestimo.setAtribuirAoEstoqueFisico(emprestimoToUpdate.getAtribuirAoEstoqueFisico());
        dbEmprestimo.setDataDeAbertura(emprestimoToUpdate.getDataDeAbertura());
        dbEmprestimo.setSituacao(emprestimoToUpdate.getSituacao());
        dbEmprestimo.getItensAReceber().clear();
        dbEmprestimo.getItensAReceber().addAll(emprestimoToUpdate.getItensAReceber());




        return this.emprestimoRepository.save(dbEmprestimo);
    }

    public void delete(Long id) {
        Emprestimo dbEmprestimo = this.findById(id);
        dbEmprestimo.getItensAReceber().forEach(itemEmprestimoAReceber -> {
            Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemEmprestimoAReceber,
                    "Cancelamento de emprestimo id : %s".formatted(dbEmprestimo.getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
        });

        this.emprestimoRepository.delete(dbEmprestimo);
    }

}
