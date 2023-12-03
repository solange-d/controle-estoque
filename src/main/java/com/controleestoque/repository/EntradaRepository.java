package com.controleestoque.repository;

import com.controleestoque.entity.Entrada;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EntradaRepository extends CrudRepository<Entrada, UUID> {

}
