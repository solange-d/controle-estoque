package com.controleestoque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table
public class Saida implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSaida;

    private LocalDate dataSaida;
    private BigDecimal valorVenda;

    @ManyToMany(mappedBy = "saidas")
    private List<Usuario> usuarios;

    @OneToOne(mappedBy = "saida")
    @JsonBackReference
    private ItemEstoque itemEstoque;
}
