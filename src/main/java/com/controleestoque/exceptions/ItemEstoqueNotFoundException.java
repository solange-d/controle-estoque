package com.controleestoque.exceptions;

public class ItemEstoqueNotFoundException extends RuntimeException{
    public ItemEstoqueNotFoundException(){
        super("Item não encontrado.");
    }
}
