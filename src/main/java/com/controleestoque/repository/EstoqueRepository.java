package com.controleestoque.repository;

import com.controleestoque.entity.Estoque;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EstoqueRepository extends CrudRepository<Estoque, UUID> {
}
