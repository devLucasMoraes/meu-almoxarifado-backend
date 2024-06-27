package com.example.meualmoxarifadobackend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nome")
    private String nome;

    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "updated_at")
    private LocalDateTime updatedAt;

    public Equipamento(Long id) {
        this.id = id;
    }
}
