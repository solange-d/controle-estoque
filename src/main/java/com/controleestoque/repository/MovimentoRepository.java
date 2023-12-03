package com.controleestoque.repository;

import com.controleestoque.entity.Movimento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovimentoRepository extends CrudRepository<Movimento, UUID> {
}
