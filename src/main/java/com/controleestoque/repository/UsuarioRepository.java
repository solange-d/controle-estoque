package com.controleestoque.repository;

import com.controleestoque.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
}
