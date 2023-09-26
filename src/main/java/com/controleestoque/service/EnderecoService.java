package com.controleestoque.service;

import com.controleestoque.entity.Endereco;
import com.controleestoque.entity.Fornecedor;
import com.controleestoque.exceptions.EnderecoNotFoundException;
import com.controleestoque.exceptions.FornecedorNotFoundException;
import com.controleestoque.mapper.EnderecoRequestToEntity;
import com.controleestoque.model.EnderecoRequest;
import com.controleestoque.model.EnderecoResponse;
import com.controleestoque.repository.EnderecoRepository;
import com.controleestoque.repository.FornecedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EnderecoService {
    private EnderecoRequestToEntity enderecoRequestToEntity;
    private EnderecoRepository enderecoRepository;
    private FornecedorService fornecedorService;
    private FornecedorRepository fornecedorRepository;


    public UUID createEnderecoFornecedor(UUID idFornecedor, EnderecoRequest enderecoRequest){
        var endereco = enderecoRequestToEntity.mapper(enderecoRequest);
        var fornecedor = updateEnderecosFornecedor(idFornecedor, endereco);
        endereco.setFornecedor(fornecedor);
        var salvarEndereco = enderecoRepository.save(endereco);
        fornecedorRepository.save(fornecedor);
        return salvarEndereco.getIdEndereco();
    }

    private Fornecedor updateEnderecosFornecedor(UUID idFornecedor, Endereco endereco){
        Fornecedor fornecedor = fornecedorService.getFornecedorById(idFornecedor);
        var enderecos = fornecedor.getEnderecos();
        enderecos.add(endereco);
        fornecedor.setEnderecos(enderecos);
        return fornecedor;
    }

    public List<Endereco> getEnderecosByFornecedorId(UUID idFornecedor) {
        return enderecoRepository.findEnderecosByFornecedorId(idFornecedor);
    }

    public Endereco getEnderecoById(UUID idEndereco){
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return endereco.orElseThrow(EnderecoNotFoundException::new);
    }

    public EnderecoResponse update(UUID idFornecedor, UUID idEndereco, EnderecoRequest enderecoRequest) {
        try {
            Endereco endereco = getEnderecoById(idEndereco);
            BeanUtils.copyProperties(enderecoRequest, endereco);
            endereco.setMunicipio(enderecoRequest.getMunicipio());
            endereco.setCep(enderecoRequest.getCep());
            endereco.setNumero(enderecoRequest.getNumero());
            endereco.setEstado(enderecoRequest.getEstado());
            endereco.setPais(enderecoRequest.getPais());
            endereco.setReferencia(enderecoRequest.getReferencia());
            endereco.setLogradouro(enderecoRequest.getLogradouro());
            enderecoRepository.save(endereco);
            var enderecoResponse = new EnderecoResponse();
            BeanUtils.copyProperties(endereco, enderecoResponse);
            return enderecoResponse;
        } catch (RuntimeException notFoundException) {
            throw new EnderecoNotFoundException();
        }


    }



    public void delete(UUID idFornecedor, UUID idEndereco) {
        try {
            getEnderecoById(idEndereco);
            enderecoRepository.deleteById(idEndereco);
        } catch (RuntimeException notFoundException) {
            throw new EnderecoNotFoundException();
        }

    }



 /*

    public Address getAddressById(Long id){
        Optional<Address> address = addressRepository.findById(id);
        return address.orElseThrow(AddressNotFoundException::new);
    }
     */


}
