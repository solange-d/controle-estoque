package com.controleestoque.exceptions;

public class EstoqueNotFoundException extends RuntimeException{
    public EstoqueNotFoundException(){
        super("Endereço não encontrado.");
    }
}
