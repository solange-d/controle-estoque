package com.controleestoque.exceptions;

public class FornecedorNotFoundException extends RuntimeException{
    public FornecedorNotFoundException(){
        super("Fornecedor não encontrado.");
    }
}
