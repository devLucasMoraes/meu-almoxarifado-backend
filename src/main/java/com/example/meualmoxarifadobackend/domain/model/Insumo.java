package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "insumos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "descricao")
    private String descricao;

    @JoinColumn(name = "valor_unt_med")
    private BigDecimal valorUntMed = BigDecimal.ZERO;

    @JoinColumn(name = "valor_unt_med_auto")
    private Boolean valorUntMedAuto = true;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque")
    private Unidade undEstoque;

    @JoinColumn(name = "estoque_minimo")
    private BigDecimal estoqueMinimo;

    @JoinColumn(name = "total_entradas")
    private BigDecimal totalEntradas = BigDecimal.ZERO;

    @JoinColumn(name = "total_saidas")
    private BigDecimal totalSaidas = BigDecimal.ZERO;

    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    public Insumo(Long idInsumo) {
        this.id = id;
    }
}
