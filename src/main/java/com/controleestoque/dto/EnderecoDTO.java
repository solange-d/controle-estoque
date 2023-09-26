package com.controleestoque.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(String cep, @NotBlank String pais, @NotBlank String estado,
                          @NotBlank String municipio, @NotBlank String bairro,
                          @NotBlank String logradouro, @NotBlank String numero, String referencia) { }
