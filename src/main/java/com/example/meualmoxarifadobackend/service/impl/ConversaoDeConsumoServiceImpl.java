package com.example.meualmoxarifadobackend.service.impl;

import com.example.meualmoxarifadobackend.domain.model.BaseItem;
import com.example.meualmoxarifadobackend.domain.model.Material;
import com.example.meualmoxarifadobackend.service.ConversaoDeConsumoService;
import com.example.meualmoxarifadobackend.service.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConversaoDeConsumoServiceImpl implements ConversaoDeConsumoService {
    public BigDecimal coverterQuantidadeParaUndidadeDeEstoque(BaseItem item, Material dbMaterial) {
        if (item.getUnidade() == dbMaterial.getCategoria().getUndEstoque()) {
            return item.getQuantidade();
        }

        var conversaoDeConsumo = dbMaterial.getCategoria().getConversoesDeConsumo().stream()
                .filter(conversao -> conversao.getUndConsumo().equals(item.getUnidade()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Conversão de %s para %s não encontrada".formatted(item.getUnidade(), dbMaterial.getCategoria().getUndEstoque())));

        return item.getQuantidade().multiply(conversaoDeConsumo.getFatorDeConversao());
    }
}
