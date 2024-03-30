package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locais_de_aplicacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LocalDeAplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nome")
    private String nome;

    public LocalDeAplicacao(Long id) {
        this.id = id;
    }
}
