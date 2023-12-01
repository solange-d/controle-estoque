package com.controleestoque.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.controleestoque.entity.Usuario;
import com.controleestoque.model.UsuarioResponse;

@Component
public class UsuarioResponseToEntity {
 
    public UsuarioResponse mapper (Usuario usuario) {
        var usuarioResponse = new UsuarioResponse();
        BeanUtils.copyProperties(usuario, usuarioResponse);
        return usuarioResponse;
    }

}