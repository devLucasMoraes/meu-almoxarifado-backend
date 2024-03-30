package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemDeCompra extends BaseItem {

    @ManyToOne
    @JoinColumn(name = "transacoes_entrada_id")
    private NfeDeCompra nfeDeCompra;

    @JoinColumn(name = "valor_ipi")
    private BigDecimal valorIpi;

    @JoinColumn(name = "descricao_fornecedora")
    private String descricaoFornecedora;

    @JoinColumn(name = "referencia_fornecedora")
    private String referenciaFornecedora;


}
