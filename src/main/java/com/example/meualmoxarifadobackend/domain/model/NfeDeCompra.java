package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nfes_de_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class NfeDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nfe")
    private String nfe;

    @JoinColumn(name = "chave_nfe")
    private String chaveNfe;

    @JoinColumn(name = "data_emissao")
    private LocalDateTime dataEmissao;

    @JoinColumn(name = "data_recebimento")
    private LocalDateTime dataRecebimento;

    @JoinColumn(name = "valor_total_produtos")
    private BigDecimal valorTotalProdutos;

    @JoinColumn(name = "valor_frete")
    private BigDecimal valorFrete;

    @JoinColumn(name = "valor_total_ipi")
    private BigDecimal valorTotalIpi;

    @JoinColumn(name = "valor_seguro")
    private BigDecimal valorSeguro;

    @JoinColumn(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @JoinColumn(name = "valor_total_nfe")
    private BigDecimal valorTotalNfe;

    @JoinColumn(name = "valor_outros")
    private BigDecimal valorOutros;

    @JoinColumn(name = "obs")
    private String obs;

    @ManyToOne
    @JoinColumn(name = "transportadoras_id")
    private Transportadora transportadora;

    @ManyToOne
    @JoinColumn(name = "fornecedoras_id")
    private Fornecedora fornecedora;

    @OneToMany(mappedBy = "nfeDeCompra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemDeCompra> itens;


}
