package com.controleestoque.repository;

import com.controleestoque.entity.Produto;
import com.controleestoque.entity.Saida;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SaidaRepository extends CrudRepository<Saida, UUID> {

    Optional<Saida> findById(UUID idSaida);
}
