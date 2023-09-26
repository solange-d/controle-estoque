package com.controleestoque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "item_estoque")
public class ItemEstoque implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idItemEstoque;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = true)
    @JsonBackReference
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_estoque")
    private Estoque estoque;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_entrada")
    @JsonBackReference
    private Entrada entrada;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_saida")
    @JsonBackReference
    private Saida saida;

}
