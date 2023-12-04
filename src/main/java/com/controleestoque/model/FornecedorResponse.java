package com.controleestoque.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorResponse {
    private UUID idFornecedor;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private boolean fabricante;

}
