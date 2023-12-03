package com.controleestoque.mapper;

import com.controleestoque.entity.Saida;
import com.controleestoque.model.SaidaResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SaidaResponseToEntity {

    public SaidaResponse mapper(Saida saida) {
        var saidaResponse = new SaidaResponse();
        BeanUtils.copyProperties(saida, saidaResponse);
        saidaResponse.setIdUsuario(saida.getUsuario().getIdUsuario());
        return saidaResponse;
    }
}
