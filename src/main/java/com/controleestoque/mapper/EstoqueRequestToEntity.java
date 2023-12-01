package com.controleestoque.mapper;

import com.controleestoque.entity.Estoque;
import com.controleestoque.model.EstoqueRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EstoqueRequestToEntity {

    public Estoque mapper (EstoqueRequest estoqueRequest) {
        var estoque = new Estoque();
        BeanUtils.copyProperties(estoqueRequest, estoque);
        return estoque;
    }
}
