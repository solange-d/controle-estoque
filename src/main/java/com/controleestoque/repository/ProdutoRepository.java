package com.controleestoque.repository;

import com.controleestoque.entity.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, UUID> {

    @Query("SELECT p FROM Produto p JOIN p.fornecedores f WHERE f.idFornecedor = :idFornecedor")
    List<Produto> findAllProdutosByFornecedor(@Param("idFornecedor") UUID idFornecedor);

    @Query("SELECT p FROM Produto p WHERE p.nome = :nome")
    List<Produto> findProdutosByNome(@Param("nome") String nome);



}
