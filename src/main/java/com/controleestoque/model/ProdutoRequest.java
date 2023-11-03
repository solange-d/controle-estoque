package com.controleestoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {
    private String marca;
    private String nome;
    private String descricao;
    private int ean;
    private double altura;
    private double largura;
    private double comprimento;
    private double peso;
}
