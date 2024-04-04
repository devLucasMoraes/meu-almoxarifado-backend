package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.controller.dto.request.AcertoEstoqueDTO;
import com.example.meualmoxarifadobackend.domain.model.*;
import com.example.meualmoxarifadobackend.service.ConversaoDeCompraService;
import com.example.meualmoxarifadobackend.service.ConversaoDeConsumoService;
import com.example.meualmoxarifadobackend.service.MaterialService;
import com.example.meualmoxarifadobackend.service.MovimentacaoFactory;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class MovimentacaoFactoryImpl implements MovimentacaoFactory {

    private final MaterialService materialService;

    private final ConversaoDeCompraService conversaoDeCompraService;

    private final ConversaoDeConsumoService conversaoDeConsumoService;

    public MovimentacaoFactoryImpl(MaterialService materialService, ConversaoDeCompraService conversaoDeCompraService, ConversaoDeConsumoService conversaoDeConsumoService) {
        this.materialService = materialService;
        this.conversaoDeCompraService = conversaoDeCompraService;
        this.conversaoDeConsumoService = conversaoDeConsumoService;
    }

    public Movimentacao criarMovimentacaoAcertoEstoque(AcertoEstoqueDTO baseItem) {
        Material dbMaterial = this.materialService.findById(baseItem.idMaterial());

        BigDecimal valorTotal = getValorTotal(baseItem, dbMaterial);

        BigDecimal valorUnitario = dbMaterial.getValorUntMed();

        int compare = dbMaterial.getQtdEmEstoqueFisico().compareTo(baseItem.quantidade());

        if (compare == 0) {
            throw new BusinessException("sem alteração");
        }

        if (compare > 0) {
            Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                    baseItem.quantidade(), valorUnitario,
                    valorTotal, baseItem.justificativa());

            return saida;
        }

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                baseItem.quantidade(), valorUnitario,
                valorTotal, baseItem.justificativa());

        return entrada;

    }

    public Movimentacao criarMovimentacaoEntrada(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa) {
        Material dbMaterial = materialService.findById(itemDeCompra.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemDeCompra, dbMaterial, nfe.getFornecedora());

        BigDecimal valorTotal = getValorTotal(itemDeCompra);

        BigDecimal valorUnitario = getValorUnitario(valorTotal, qtdConvertida);

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return entrada;
    }

    public Movimentacao criarMovimentacaoSaida(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa) {
        Material dbMaterial = materialService.findById(itemDeCompra.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemDeCompra, dbMaterial, nfe.getFornecedora());

        BigDecimal valorTotal = getValorTotal(itemDeCompra);

        BigDecimal valorUnitario = getValorUnitario(valorTotal, qtdConvertida);

        Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return saida;
    }

    public Movimentacao criarMovimentacaoSaida(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemRequisicao, dbMaterial);

        BigDecimal valorTotal = getValorTotal(itemRequisicao, dbMaterial);

        BigDecimal valorUnitario = dbMaterial.getValorUntMed();

        Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return saida;
    }

    public Movimentacao criarMovimentacaoEntrada(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemRequisicao, dbMaterial);

        BigDecimal valorTotal = getValorTotal(itemRequisicao, dbMaterial);

        BigDecimal valorUnitario = getValorUnitario(valorTotal, qtdConvertida);

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return entrada;
    }

    public Movimentacao criarMovimentacaoSaida(BaseItem baseItem, String justificativa) {
        Material dbMaterial = this.materialService.findById(baseItem.getMaterial().getId());

        BigDecimal valorTotal = getValorTotal(baseItem, dbMaterial);

        BigDecimal valorUnitario = dbMaterial.getValorUntMed();

        Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                baseItem.getQuantidade(), valorUnitario,
                valorTotal, justificativa);

        return saida;

    }

    public Movimentacao criarMovimentacaoEntrada(BaseItem baseItem, String justificativa) {
        Material dbMaterial = this.materialService.findById(baseItem.getMaterial().getId());

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                baseItem.getQuantidade(), baseItem.getValorUnitario(),
                baseItem.getValorTotal(), justificativa);

        return entrada;
    }

    private Movimentacao criarMovimentacaoBase(Material material, Tipo tipo,
                                               BigDecimal qtd, BigDecimal valorUnit,
                                               BigDecimal valorTotal, String justificativa) {

        Movimentacao mov = new Movimentacao();

        mov.setMaterial(material);
        mov.setTipo(tipo);
        mov.setData(LocalDateTime.now());
        mov.setQuantidade(qtd);
        mov.setUnidade(material.getCategoria().getUndEstoque());
        mov.setValorUnt(valorUnit);
        mov.setValorTotal(valorTotal);
        mov.setJustificativa(justificativa);

        return mov;
    }

    private BigDecimal getQtdConvertida(ItemDeCompra item, Material material, Fornecedora fornecedora) {
        return conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(item, fornecedora, material);
    }

    private BigDecimal getQtdConvertida(ItemRequisicao item, Material material) {
        return conversaoDeConsumoService.coverterQuantidadeParaUndidadeDeEstoque(item, material);
    }

    private BigDecimal getValorTotal(ItemDeCompra itemDeCompra) {
        return itemDeCompra.getValorUnitario().multiply(itemDeCompra.getQuantidade()).add(itemDeCompra.getValorIpi());
    }

    private BigDecimal getValorTotal(BaseItem item, Material dbMaterial) {
        return dbMaterial.getValorUntMed().multiply(item.getQuantidade());
    }

    private BigDecimal getValorTotal(AcertoEstoqueDTO item, Material dbMaterial) {
        return dbMaterial.getValorUntMed().multiply(item.quantidade());
    }

    private BigDecimal getValorUnitario(BigDecimal valorTotal, BigDecimal qtd) {
        return valorTotal.divide(qtd, 4, RoundingMode.HALF_UP);
    }


}
