package com.controleestoque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name="endereco")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idEndereco;
    private int cep;
    private String pais;
    private String estado;
    private String municipio;
    private String bairro;
    private String logradouro;
    private String numero;
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", nullable = false)
    @JsonBackReference
    private Fornecedor fornecedor;

}
