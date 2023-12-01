package com.controleestoque.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.controleestoque.entity.Usuario;
import com.controleestoque.model.UsuarioRequest;

@Component
public class UsuarioRequestToEntity {
    
    public Usuario mapper (UsuarioRequest usuarioRequest) {
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioRequest, usuario);
        return usuario;
    }

}
