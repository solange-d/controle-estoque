package com.controleestoque.model;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class ItemEstoqueResponse {
    @NotNull
    private UUID idItemEstoque;
    @NotNull
    private UUID idEstoque;
    @NotNull
    private UUID idProduto;
    @NotNull
    private UUID idEntrada;
    private UUID idSaida;
}
