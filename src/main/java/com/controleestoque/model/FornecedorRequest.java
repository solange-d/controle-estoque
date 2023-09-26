package com.controleestoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorRequest {
    private String nome;
    private String cnpj;
    private String email;
    private int telefone;
    private boolean fabricante;
}
