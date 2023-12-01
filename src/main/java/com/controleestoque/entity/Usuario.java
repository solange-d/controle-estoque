package com.controleestoque.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUsuario;

    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private boolean administrador;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Movimento> movimentos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Saida> saidas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Entrada> entradas;

}
