package com.controleestoque.mapper;

import com.controleestoque.entity.Movimento;
import com.controleestoque.model.MovimentoRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MovimentoRequestToEntity {

    public Movimento mapper (MovimentoRequest movimentoRequest) {
        var movimento = new Movimento();
        BeanUtils.copyProperties(movimentoRequest, movimento);
        movimento.getUsuario().setIdUsuario(movimentoRequest.getIdUsuario());
        return movimento;
    }

}
