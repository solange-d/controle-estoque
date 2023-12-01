package com.controleestoque.mapper;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.model.FornecedorRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FornecedorRequestToEntity {

    public Fornecedor mapper (FornecedorRequest fornecedorRequest){
        Fornecedor fornecedor = new Fornecedor();
        BeanUtils.copyProperties(fornecedorRequest, fornecedor);
        return fornecedor;
    }
}
