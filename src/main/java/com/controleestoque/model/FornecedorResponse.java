package com.controleestoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorResponse {
    private String nome;
    private String cnpj;
    private String email;
    private int telefone;
    private boolean fabricante;

}
