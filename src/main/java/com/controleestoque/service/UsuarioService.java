package com.controleestoque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.controleestoque.entity.Usuario;
import com.controleestoque.exceptions.UsuarioNotFoundException;
import com.controleestoque.mapper.UsuarioRequestToEntity;
import com.controleestoque.mapper.UsuarioResponseToEntity;
import com.controleestoque.model.UsuarioRequest;
import com.controleestoque.model.UsuarioResponse;
import com.controleestoque.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private UsuarioRequestToEntity usuarioRequestToEntity;
    private UsuarioResponseToEntity usuarioResponseToEntity;
    private UsuarioRepository usuarioRepository;

    public UUID create(UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRequestToEntity.mapper(usuarioRequest);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return savedUsuario.getIdUsuario();
    }

    public UsuarioResponse getUsuarioById(UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNotFoundException::new);
        UsuarioResponse response = usuarioResponseToEntity.mapper(usuario);
        return response;
    }
    
    public UsuarioResponse getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
        UsuarioResponse response = usuarioResponseToEntity.mapper(usuario);
        return response;
    }

    public List<UsuarioResponse> getAll() {
        var usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> usuarioResponseList = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioResponse response = usuarioResponseToEntity.mapper(usuario);
            usuarioResponseList.add(response);
        }

        return usuarioResponseList;
    }

    public UsuarioResponse update(UUID idUsuario, UsuarioRequest usuarioRequest) {
        try {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(UsuarioNotFoundException::new);

            // Atualize as propriedades específicas do Usuário
            usuario.setNome(usuarioRequest.getNome());
            usuario.setCpf(usuarioRequest.getCpf());
            usuario.setEmail(usuarioRequest.getEmail());
            usuario.setSenha(usuarioRequest.getSenha());
            usuario.setAdministrador(usuarioRequest.isAdministrador());
            usuarioRepository.save(usuario);

            UsuarioResponse usuarioResponse = new UsuarioResponse();
            BeanUtils.copyProperties(usuario, usuarioResponse);

            return usuarioResponse;
        } catch (RuntimeException notFoundException) {
            throw new UsuarioNotFoundException();
        }
    }

    public void delete(UUID idUsuario) {
        try {
            usuarioRepository.findById(idUsuario);
            usuarioRepository.deleteById(idUsuario);
        } catch (UsuarioNotFoundException notFoundException) {
            throw new UsuarioNotFoundException();
        }
    }
}
