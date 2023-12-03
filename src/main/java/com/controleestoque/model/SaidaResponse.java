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
public class SaidaResponse {
    private UUID idSaida;
    private LocalDate dataSaida;
    private BigDecimal valorVenda;
    private UUID idUsuario;
}
