package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
public class BaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "insumos_id")
    private Insumo insumo;

    @JoinColumn(name = "quantidade")
    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "unidade")
    private Unidade unidade;

    @JoinColumn(name = "valor_unitario")
    private BigDecimal valorUnitario = BigDecimal.ZERO;

    public BigDecimal getValorTotal() {
        return this.quantidade.multiply(this.valorUnitario);
    }
}
