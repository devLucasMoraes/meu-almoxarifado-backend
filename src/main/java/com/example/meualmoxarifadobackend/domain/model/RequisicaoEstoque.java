package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requisicoes_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RequisicaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_requisicao")
    private LocalDateTime dataRequisicao;

    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "ordem_producao")
    private String ordemProducao;

    @Column(name = "obs")
    private String obs;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requisitantes_id")
    private Requisitante requisitante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipamentos_id")
    private Equipamento equipamento;

    @OneToMany(mappedBy = "requisicaoEstoque", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RequisicaoEstoqueItem> itens;

}
