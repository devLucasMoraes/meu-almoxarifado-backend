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
    @JoinColumn(name = "materiais_id")
    private Material material;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "unidade")
    private Unidade unidade;

    @JoinColumn(name = "quantidade")
    private BigDecimal quantidade;

    @JoinColumn(name = "valor_unitario")
    private BigDecimal valorUnitario = BigDecimal.ZERO;

    public BigDecimal getValorTotal() {
        return this.quantidade.multiply(this.valorUnitario);
    }
}
