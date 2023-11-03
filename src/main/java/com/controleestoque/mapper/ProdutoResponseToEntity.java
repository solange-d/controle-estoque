package com.controleestoque.mapper;

import com.controleestoque.entity.Produto;
import com.controleestoque.model.ProdutoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProdutoResponseToEntity {

    public ProdutoResponse mapper(Produto produto) {
        ProdutoResponse produtoResponse = new ProdutoResponse();
        BeanUtils.copyProperties(produto, produtoResponse);
        return produtoResponse;
    }
}
