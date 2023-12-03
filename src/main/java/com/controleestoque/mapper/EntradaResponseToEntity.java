package com.controleestoque.mapper;

import com.controleestoque.entity.Entrada;
import com.controleestoque.model.EntradaRequest;
import com.controleestoque.model.EntradaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EntradaResponseToEntity {

    public EntradaResponse mapper(Entrada entrada) {
        var entradaResponse = new EntradaResponse();
        BeanUtils.copyProperties(entrada, entradaResponse);
        entradaResponse.setIdUsuario(entrada.getUsuario().getIdUsuario());
        return entradaResponse;
    }

}
