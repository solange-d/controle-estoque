package com.controleestoque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "entrada")
public class Entrada implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idEntrada;

    private LocalDate dataEntrada;
    private BigDecimal custoAquisicao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @OneToOne(mappedBy = "entrada")
    @JsonBackReference
    private ItemEstoque itemEstoque;

    public Usuario getUsuario() {
        if(usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }
}
