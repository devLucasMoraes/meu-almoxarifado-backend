package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.Movimentacao;
import com.example.meualmoxarifadobackend.domain.model.NfeDeCompra;
import com.example.meualmoxarifadobackend.domain.repository.NfeDeCompraRepository;
import com.example.meualmoxarifadobackend.service.*;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import com.example.meualmoxarifadobackend.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NfeDeCompraServiceImpl implements NfeDeCompraService {

    private final NfeDeCompraRepository nfeDeCompraRepository;

    private final MovimentacaoService movimentacaoService;

    private final FornecedoraService fornecedoraService;

    private final TransportadoraService transportadoraService;

    private final MaterialService materialService;

    private final MovimentacaoFactory movimentacaoFactory;

    public NfeDeCompraServiceImpl(
            NfeDeCompraRepository nfeDeCompraRepository,
            MovimentacaoService movimentacaoService,
            FornecedoraService fornecedoraService,
            TransportadoraService transportadoraService,
            MaterialService materialService,
            MovimentacaoFactory movimentacaoFactory
    ) {
        this.nfeDeCompraRepository = nfeDeCompraRepository;
        this.movimentacaoService = movimentacaoService;
        this.fornecedoraService = fornecedoraService;
        this.transportadoraService = transportadoraService;
        this.materialService = materialService;
        this.movimentacaoFactory = movimentacaoFactory;
    }

    @Transactional(readOnly = true)
    public Page<NfeDeCompra> findAll(Pageable pageable) {
        return this.nfeDeCompraRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public NfeDeCompra findById(Long id) {
        return this.nfeDeCompraRepository.findById(id).orElseThrow(() -> new NotFoundException("N-fe"));
    }

    @Transactional
    public NfeDeCompra create(NfeDeCompra nfeDeCompraToCreate) {

        if (!this.fornecedoraService.existsById(nfeDeCompraToCreate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if (!this.transportadoraService.existsById(nfeDeCompraToCreate.getTransportadora().getId())) {
            throw new NotFoundException("Transportadora");
        }

        nfeDeCompraToCreate.getItens().forEach(itemDeCompra -> {
            if (!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToCreate);

            Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(itemDeCompra, nfeDeCompraToCreate,
                    "Entrada de NFe %s".formatted(nfeDeCompraToCreate.getNfe()));

            this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);

        });


        return this.nfeDeCompraRepository.save(nfeDeCompraToCreate);
    }

    @Transactional
    public NfeDeCompra update(Long id, NfeDeCompra nfeDeCompraToUpdate) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);

        if (!dbNfeDeCompra.getId().equals(nfeDeCompraToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (!this.fornecedoraService.existsById(nfeDeCompraToUpdate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if (!this.transportadoraService.existsById(nfeDeCompraToUpdate.getTransportadora().getId())) {
            throw new NotFoundException("Transportadora");
        }

        dbNfeDeCompra.getItens().forEach(itemDeCompra -> {
            Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemDeCompra, nfeDeCompraToUpdate,
                    "Alteração de NFe id : %s".formatted(dbNfeDeCompra.getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
        });

        nfeDeCompraToUpdate.getItens().forEach(itemDeCompra -> {
            if (!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToUpdate);
            Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(itemDeCompra, nfeDeCompraToUpdate,
                    "Alteração de NFe id : %s".formatted(dbNfeDeCompra.getId()));

            this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);
        });

        dbNfeDeCompra.setNfe(nfeDeCompraToUpdate.getNfe());
        dbNfeDeCompra.setChaveNfe(nfeDeCompraToUpdate.getChaveNfe());
        dbNfeDeCompra.setDataEmissao(nfeDeCompraToUpdate.getDataEmissao());
        dbNfeDeCompra.setDataRecebimento(nfeDeCompraToUpdate.getDataRecebimento());
        dbNfeDeCompra.setValorTotalProdutos(nfeDeCompraToUpdate.getValorTotalProdutos());
        dbNfeDeCompra.setValorFrete(nfeDeCompraToUpdate.getValorFrete());
        dbNfeDeCompra.setValorTotalIpi(nfeDeCompraToUpdate.getValorTotalIpi());
        dbNfeDeCompra.setValorSeguro(nfeDeCompraToUpdate.getValorSeguro());
        dbNfeDeCompra.setValorDesconto(nfeDeCompraToUpdate.getValorDesconto());
        dbNfeDeCompra.setValorTotalNfe(nfeDeCompraToUpdate.getValorTotalNfe());
        dbNfeDeCompra.setValorOutros(nfeDeCompraToUpdate.getValorOutros());
        dbNfeDeCompra.setObs(nfeDeCompraToUpdate.getObs());
        dbNfeDeCompra.setTransportadora(nfeDeCompraToUpdate.getTransportadora());
        dbNfeDeCompra.setFornecedora(nfeDeCompraToUpdate.getFornecedora());
        dbNfeDeCompra.getItens().clear();
        dbNfeDeCompra.getItens().addAll(nfeDeCompraToUpdate.getItens());


        return this.nfeDeCompraRepository.save(dbNfeDeCompra);
    }

    @Transactional
    public void delete(Long id) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);
        dbNfeDeCompra.getItens().forEach(itemDeCompra -> {
            Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(itemDeCompra, dbNfeDeCompra,
                    "Cancelamento de NFe id : %s".formatted(dbNfeDeCompra.getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
        });

        this.nfeDeCompraRepository.delete(dbNfeDeCompra);
    }

}
