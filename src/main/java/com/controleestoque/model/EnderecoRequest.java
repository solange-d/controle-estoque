package com.controleestoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequest {
    private int cep;
    private String pais;
    private String estado;
    private String municipio;
    private String bairro;
    private String logradouro;
    private String numero;
    private String referencia;
}
