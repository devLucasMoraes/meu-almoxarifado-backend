package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque")
    private Unidade undEstoque;

    @JoinColumn(name = "estoque_minimo")
    private BigDecimal estoqueMinimo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<ConversaoDeConsumo> conversoesDeConsumo;

    public Categoria(Long id) {
        this.id = id;
    }
}
