package com.controleestoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueResponse {
    private UUID idEstoque;
    private String localizacao;
    private String andar;
    private String fila;
    private String rua;
    private String prateleira;
}
