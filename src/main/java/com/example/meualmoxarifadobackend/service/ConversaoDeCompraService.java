package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.BaseItem;
import com.example.meualmoxarifadobackend.domain.model.Fornecedora;
import com.example.meualmoxarifadobackend.domain.model.Material;

import java.math.BigDecimal;

public interface ConversaoDeCompraService {
    BigDecimal coverterQuantidadeParaUndidadeDeEstoque(BaseItem item, Fornecedora fornecedora, Material dbMaterial);
}
