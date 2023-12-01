package com.controleestoque.mapper;

import com.controleestoque.entity.Produto;
import com.controleestoque.model.ProdutoRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProdutoRequestToEntity {

    public Produto mapper (ProdutoRequest produtoRequest){
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoRequest, produto);
        return produto;
    }

}
