package com.controleestoque.mapper;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.model.FornecedorResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FornecedorResponseToEntity {
    public FornecedorResponse mapper(Fornecedor fornecedor) {
        FornecedorResponse fornecedorResponse = new FornecedorResponse();
        BeanUtils.copyProperties(fornecedor, fornecedorResponse);
        return fornecedorResponse;
    }
}
