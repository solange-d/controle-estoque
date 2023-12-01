package com.controleestoque.model;

import com.controleestoque.entity.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponse {
    private UUID idEndereco;
    private int cep;
    private String pais;
    private String estado;
    private String municipio;
    private String bairro;
    private String logradouro;
    private String numero;
    private String referencia;
    private Fornecedor fornecedor;

}
