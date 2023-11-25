package com.controleestoque.repository;

import com.controleestoque.entity.Endereco;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, UUID> {

    @Query("SELECT e FROM Endereco e WHERE e.fornecedor.idFornecedor = :idFornecedor")
    List<Endereco> findEnderecosByFornecedorId(@Param("idFornecedor") UUID idFornecedor);

}
