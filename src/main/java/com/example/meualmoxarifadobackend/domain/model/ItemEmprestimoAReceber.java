package com.example.meualmoxarifadobackend.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "itens_emprestimo_a_receber")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemEmprestimoAReceber extends BaseItem {

    @ManyToOne
    @JoinColumn(name = "emprestimos_e_trocas_id")
    private Emprestimo emprestimo;

}
