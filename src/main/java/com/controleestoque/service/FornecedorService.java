package com.controleestoque.service;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.exceptions.FornecedorNotFoundException;
import com.controleestoque.mapper.FornecedorRequestToEntity;
import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.repository.FornecedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FornecedorService {
    public FornecedorRepository fornecedorRepository;
    public FornecedorRequestToEntity fornecedorRequestToEntity;

    public UUID create(FornecedorRequest fornecedorRequest){
        var fornecedor = fornecedorRequestToEntity.mapper(fornecedorRequest);
        Fornecedor response = fornecedorRepository.save(fornecedor);
        return response.getIdFornecedor();
    }

    public Fornecedor getFornecedorById(UUID idFornecedor){
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(idFornecedor);
        return fornecedor.orElseThrow(FornecedorNotFoundException::new);
    }


}
