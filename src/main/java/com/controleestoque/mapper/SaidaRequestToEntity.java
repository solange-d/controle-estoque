package com.controleestoque.mapper;

import com.controleestoque.entity.Saida;
import com.controleestoque.model.SaidaRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SaidaRequestToEntity {

    public Saida mapper(SaidaRequest saidaRequest) {
        var saida = new Saida();
        saida.setDataSaida(saidaRequest.getDataSaida());
        saida.setValorVenda(saidaRequest.getValorVenda());
        return saida;
    }
}
