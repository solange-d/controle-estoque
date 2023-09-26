package com.controleestoque.exceptions;

public class EnderecoNotFoundException extends RuntimeException{
    public EnderecoNotFoundException(){
        super("Endereço não encontrado.");
    }
}
