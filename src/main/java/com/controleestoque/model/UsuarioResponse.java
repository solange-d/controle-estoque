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
public class UsuarioResponse {
    private UUID idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private boolean administrador;
}
