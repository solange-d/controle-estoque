package com.controleestoque.repository;

import com.controleestoque.entity.Fornecedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, UUID> {
}
