package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conversoes_de_consumo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ConversaoDeConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_consumo")
    private Unidade undConsumo;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque")
    private Unidade undEstoque;

    @JoinColumn(name = "fator_de_conversao")
    private BigDecimal fatorDeConversao;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

}
