package com.controleestoque.repository;

import com.controleestoque.entity.ItemEstoque;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemEstoqueRepository extends CrudRepository<ItemEstoque, UUID> {
}
