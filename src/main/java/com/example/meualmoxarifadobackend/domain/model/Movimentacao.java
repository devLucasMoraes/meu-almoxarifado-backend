package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacaoes")
@Getter
@Setter
@NoArgsConstructor
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "materiais_id")
    private Material material;

    private LocalDateTime data;

    private BigDecimal valorUnt;

    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    private Unidade unidade;

    private BigDecimal valorTotal;

    private String justificativa;


}
