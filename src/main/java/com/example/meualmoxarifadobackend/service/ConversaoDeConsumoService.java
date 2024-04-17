package com.example.meualmoxarifadobackend.service;

import com.example.meualmoxarifadobackend.domain.model.BaseItem;
import com.example.meualmoxarifadobackend.domain.model.Material;

import java.math.BigDecimal;

public interface ConversaoDeConsumoService {
    BigDecimal coverterQuantidadeParaUndidadeDeEstoque(BaseItem item, Material dbMaterial);
}
