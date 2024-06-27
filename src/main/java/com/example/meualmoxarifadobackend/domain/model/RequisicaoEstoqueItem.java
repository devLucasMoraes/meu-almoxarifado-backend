package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "requisicoes_estoque_itens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RequisicaoEstoqueItem extends BaseItem{

    @ManyToOne
    @JoinColumn(name = "requisicoes_estoque_id")
    private RequisicaoEstoque requisicaoEstoque;

}
