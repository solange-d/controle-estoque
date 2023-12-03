package com.controleestoque.exceptions;

public class MovimentoNotFoundException extends RuntimeException {
    public MovimentoNotFoundException() {
        super("Movimento não encontrado.");
    }
}
