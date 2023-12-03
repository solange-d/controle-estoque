package com.controleestoque.mapper;

import com.controleestoque.entity.Entrada;
import com.controleestoque.model.EntradaRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EntradaRequestToEntity {

    public Entrada mapper(EntradaRequest entradaRequest) {
        var entrada = new Entrada();
        BeanUtils.copyProperties(entradaRequest, entrada);
        entrada.getUsuario().setIdUsuario(entradaRequest.getIdUsuario());
        return entrada;
    }
}
