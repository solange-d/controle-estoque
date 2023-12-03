package com.controleestoque.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.controleestoque.entity.ItemEstoque;
import com.controleestoque.model.ItemEstoqueResponse;

@Component
public class ItemEstoqueResponseToEntity {

    public ItemEstoqueResponse mapper(ItemEstoque itemEstoque) {
        var itemEstoqueResponse = new ItemEstoqueResponse();
        BeanUtils.copyProperties(itemEstoque, itemEstoqueResponse);
        return itemEstoqueResponse;
    }

}