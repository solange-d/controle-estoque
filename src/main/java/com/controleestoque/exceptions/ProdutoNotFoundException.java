package com.controleestoque.exceptions;

public class ProdutoNotFoundException extends RuntimeException{

    public ProdutoNotFoundException(){
        super("Produto não encontrado.");
    }
}
