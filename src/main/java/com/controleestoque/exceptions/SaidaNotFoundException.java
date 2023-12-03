package com.controleestoque.exceptions;

public class SaidaNotFoundException extends RuntimeException {
    public SaidaNotFoundException() {
        super("Saida não encontrada.");
    }
}
