package com.controleestoque.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "estoque")
public class Estoque implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idEstoque;

    private String localizacao;
    private String andar;
    private String fila;
    private String rua;
    private String prateleira;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
    private List<ItemEstoque> itens;
}
