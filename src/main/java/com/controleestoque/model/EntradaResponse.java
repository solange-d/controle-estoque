package com.controleestoque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaResponse {
    private UUID idEntrada;
    private LocalDate dataEntrada;
    private BigDecimal custoAquisicao;
    private UUID idUsuario;
}
