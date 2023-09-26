package com.controleestoque.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
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

    @ManyToMany
    @JoinTable(name = "responsavel_movimento",
        joinColumns = {@JoinColumn(name = "id_usuario")},
        inverseJoinColumns = {@JoinColumn(name = "id_movimento")}
    )
    private List<Movimento> movimentos;

    @ManyToMany
    @JoinTable(name = "responsavel_saida",
            joinColumns = {@JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {@JoinColumn(name = "id_saida")}
    )
    private List<Saida> saidas;

    @ManyToMany
    @JoinTable(name = "responsavel_entrada",
            joinColumns = {@JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {@JoinColumn(name = "id_entrada")}
    )
    private List<Entrada> entradas;

}
