package com.controleestoque.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduto;
    private String marca;
    private String nome;
    private String descricao;
    private String ean;
    private double altura;
    private double largura;
    private double comprimento;
    private double peso;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemEstoque> itens;

    @ManyToMany(mappedBy = "produtos")
    private List<Fornecedor> fornecedores;

}
