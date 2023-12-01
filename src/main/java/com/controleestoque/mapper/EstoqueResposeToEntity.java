package com.controleestoque.mapper;

import com.controleestoque.entity.Estoque;
import com.controleestoque.model.EstoqueRequest;
import com.controleestoque.model.EstoqueResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EstoqueResposeToEntity {

    public EstoqueResponse mapper (Estoque estoque) {
        var estoqueResponse = new EstoqueResponse();
        BeanUtils.copyProperties(estoque, estoqueResponse);
        return estoqueResponse;
    }
}
