package com.controleestoque.mapper;

import com.controleestoque.entity.Saida;
import com.controleestoque.model.SaidaRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SaidaRequestToEntity {

    public Saida mapper(SaidaRequest saidaRequest) {
        var saida = new Saida();
        BeanUtils.copyProperties(saidaRequest, saida);
        saida.getUsuario().setIdUsuario(saidaRequest.getIdUsuario());
        return saida;
    }
}
