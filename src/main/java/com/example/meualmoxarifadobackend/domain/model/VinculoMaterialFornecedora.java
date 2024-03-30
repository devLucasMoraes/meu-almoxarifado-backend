package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "vinculos_materiais_fornecedoras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VinculoMaterialFornecedora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "referencia_fornecedora")
    private String referenciaFornecedora;

    @JoinColumn(name = "descricao_fornecedora")
    private String descricaoFornecedora;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "fornecedoras_id")
    private Fornecedora fornecedora;

    @OneToMany(mappedBy = "vinculoComFornecedoras", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ConversaoDeCompra> conversaoDeCompras;

}
