package com.controleestoque.mapper;

import com.controleestoque.entity.Endereco;
import com.controleestoque.model.EnderecoRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EnderecoRequestToEntity {

    public Endereco mapper (EnderecoRequest enderecoRequest){
        var endereco = new Endereco();
        BeanUtils.copyProperties(enderecoRequest, endereco);
        return endereco;
    }

}
