package com.controleestoque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFornecedor;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;

    @ManyToMany
    @JoinTable(name = "fornecedor_produto",
    joinColumns = {@JoinColumn(name = "id_fornecedor")},
    inverseJoinColumns = {@JoinColumn(name = "id_produto")})
    @JsonBackReference
    private List<Produto> produtos;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.REMOVE)
    @Column(name = "enderecos")
    @JsonManagedReference
    private List<Endereco> enderecos;

}
