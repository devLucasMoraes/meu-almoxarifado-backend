package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "emprestimos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Emprestimo {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private Boolean AtribuirAoEstoqueFisico;

    private LocalDateTime dataDeAbertura;

    private BigDecimal valorTotal;

    @ManyToOne
    private Fornecedora fornecedora;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemEmprestimoAReceber> itensAReceber;

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemEmprestimoAPagar> itensAPagar;

}
