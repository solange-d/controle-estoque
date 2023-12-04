package com.controleestoque.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

    private UUID idProduto;
    private String marca;
    private String nome;
    private String descricao;
    private String ean;
    private double altura;
    private double largura;
    private double comprimento;
    private double peso;
}
