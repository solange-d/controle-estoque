package com.controleestoque.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.controleestoque.entity.ItemEstoque;
import com.controleestoque.model.ItemEstoqueRequest;

@Component
public class ItemEstoqueRequestToEntity {
    
    public ItemEstoque mapper (ItemEstoqueRequest itemEstoqueRequest) {
        var itemEstoque = new ItemEstoque();
        BeanUtils.copyProperties(itemEstoqueRequest, itemEstoque);
        return itemEstoque;
    }

}
