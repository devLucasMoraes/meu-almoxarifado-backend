package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requisicoes_de_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RequisicaoDeEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "data_requisicao")
    private LocalDateTime dataRequisicao;

    @JoinColumn(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @JoinColumn(name = "obs")
    private String obs;

    @JoinColumn(name = "ordem_producao")
    private String ordemProducao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requisitantes_id")
    private Requisitante requisitante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locais_de_aplicacao_id")
    private LocalDeAplicacao localDeAplicacao;

    @OneToMany(mappedBy = "requisicaoDeEstoque", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemRequisicao> itens;

}
