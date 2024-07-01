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

    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    @Column(name = "valor_unt_med", nullable = false)
    private BigDecimal valorUntMed = BigDecimal.ZERO;

    @Column(name = "valor_unt_med_auto", nullable = false)
    private Boolean valorUntMedAuto = false;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque", nullable = false)
    private Unidade undEstoque;

    @Column(name = "estoque_minimo", nullable = false)
    private BigDecimal estoqueMinimo;

    @Column(name = "total_entradas", nullable = false)
    private BigDecimal totalEntradas = BigDecimal.ZERO;

    @Column(name = "total_saidas", nullable = false)
    private BigDecimal totalSaidas = BigDecimal.ZERO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "categorias_id", nullable = false)
    private Categoria categoria;

    public Insumo(Long idInsumo) {
        this.id = idInsumo;
    }
}
