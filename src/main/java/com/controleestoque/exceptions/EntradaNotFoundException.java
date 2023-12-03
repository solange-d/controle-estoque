package com.controleestoque.exceptions;

public class EntradaNotFoundException extends RuntimeException {
    public EntradaNotFoundException() {
        super("Entrada não encontrada");
    }
}
