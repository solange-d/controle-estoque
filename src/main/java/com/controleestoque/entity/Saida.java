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
@Table(name = "saida")
public class Saida implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSaida;

    private LocalDate dataSaida;
    private BigDecimal valorVenda;

    @OneToOne(mappedBy = "saida")
    @JsonBackReference
    private ItemEstoque itemEstoque;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    public Usuario getUsuario() {
        if(usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }
}
