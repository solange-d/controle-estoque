package com.controleestoque.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MovimentoRequest {
    private UUID idMovimento;
    private LocalDate dataMovimentacao;
    private UUID idUsuario;
}
