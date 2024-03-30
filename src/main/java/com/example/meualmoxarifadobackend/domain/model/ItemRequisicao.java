package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itens_requisicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemRequisicao extends BaseItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requisicoes_de_estoque_id")
    private RequisicaoDeEstoque requisicaoDeEstoque;

}
