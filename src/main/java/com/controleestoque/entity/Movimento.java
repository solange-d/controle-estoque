package com.controleestoque.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movimento")
public class Movimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idMovimento;

    private LocalDate dataMovimentacao;

    @ManyToMany(mappedBy = "movimentos")
    private List<Usuario> usuarios;
}
