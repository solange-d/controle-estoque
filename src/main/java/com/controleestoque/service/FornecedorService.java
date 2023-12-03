package com.controleestoque.service;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.exceptions.FornecedorNotFoundException;
import com.controleestoque.mapper.FornecedorRequestToEntity;
import com.controleestoque.mapper.FornecedorResponseToEntity;
import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.model.FornecedorResponse;
import com.controleestoque.repository.FornecedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FornecedorService {
    public FornecedorRepository fornecedorRepository;
    public FornecedorRequestToEntity fornecedorRequestToEntity;

    public FornecedorResponseToEntity fornecedorResponseToEntity;

    public UUID create(FornecedorRequest fornecedorRequest){
        var fornecedor = fornecedorRequestToEntity.mapper(fornecedorRequest);
        Fornecedor response = fornecedorRepository.save(fornecedor);
        return response.getIdFornecedor();
    }

    public FornecedorResponse getFornecedorById(UUID idFornecedor){
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(idFornecedor);
        if(fornecedor.isEmpty()){
            throw new FornecedorNotFoundException();
        }
        FornecedorResponse response = fornecedorResponseToEntity.mapper(fornecedor.get());
        return response;
    }

    public Fornecedor getById(UUID idFornecedor){
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(idFornecedor);
        return fornecedor.orElseThrow(FornecedorNotFoundException::new);
    }


    public List<FornecedorResponse> getAll() {
        var fornecedores = fornecedorRepository.findAll();
        List<FornecedorResponse> fornecedorResponseList = new ArrayList<>();

        for (Fornecedor fornecedor : fornecedores) {
            FornecedorResponse response = fornecedorResponseToEntity.mapper(fornecedor);
            fornecedorResponseList.add(response);
        }

        return fornecedorResponseList;
    }


     public FornecedorResponse update(UUID idFornecedor, FornecedorRequest fornecedorRequest) {
        try {
            Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor).get();
            BeanUtils.copyProperties(fornecedorRequest, fornecedor);
            fornecedor.setNome(fornecedorRequest.getNome());
            fornecedor.setCnpj(fornecedorRequest.getCnpj());
            fornecedor.setEmail(fornecedor.getEmail());
            fornecedor.setTelefone(fornecedor.getTelefone());
            fornecedorRepository.save(fornecedor);
            var fornecedorResponse = new FornecedorResponse();
            BeanUtils.copyProperties(fornecedor, fornecedorResponse);
            return fornecedorResponse;
        } catch (RuntimeException notFoundException) {
            throw new FornecedorNotFoundException();
        }
    }


    public void delete(UUID idFornecedor) {
        try {
            getFornecedorById(idFornecedor);
            fornecedorRepository.deleteById(idFornecedor);
        } catch (RuntimeException notFoundException) {
            throw new FornecedorNotFoundException();
        }

    }

}
