package com.controleestoque.mapper;

import com.controleestoque.entity.Movimento;
import com.controleestoque.model.MovimentoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MovimentoResponseToEntity {

    public MovimentoResponse mapper (Movimento movimento) {
        var movimentoResponse = new MovimentoResponse();
        BeanUtils.copyProperties(movimento, movimentoResponse);
        movimentoResponse.setIdUsuario(movimento.getUsuario().getIdUsuario());
        return movimentoResponse;
    }

}
