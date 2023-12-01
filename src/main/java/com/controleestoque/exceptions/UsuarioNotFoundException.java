package com.controleestoque.exceptions;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(){
        super("Usuário não encontrado.");
    }
}
