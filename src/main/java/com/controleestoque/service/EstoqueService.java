package com.controleestoque.service;

import com.controleestoque.entity.Estoque;
import com.controleestoque.exceptions.EstoqueNotFoundException;
import com.controleestoque.mapper.EstoqueRequestToEntity;
import com.controleestoque.mapper.EstoqueResposeToEntity;
import com.controleestoque.model.EstoqueRequest;
import com.controleestoque.model.EstoqueResponse;
import com.controleestoque.repository.EstoqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EstoqueService {
    private EstoqueRequestToEntity estoqueRequestToEntity;
    private EstoqueResposeToEntity estoqueResponseToEntity;
    private EstoqueRepository estoqueRepository;

    public UUID create(EstoqueRequest estoqueRequest) {
        Estoque estoque = estoqueRequestToEntity.mapper(estoqueRequest);
        Estoque savedEstoque = estoqueRepository.save(estoque);
        return savedEstoque.getIdEstoque();
    }

    public EstoqueResponse getEstoqueById(UUID idEstoque) {
        Estoque estoque = estoqueRepository.findById(idEstoque).orElseThrow(EstoqueNotFoundException::new);
        EstoqueResponse response = estoqueResponseToEntity.mapper(estoque);
        return response;
    }

    public List<EstoqueResponse> getAll() {
        var estoques = estoqueRepository.findAll();
        List<EstoqueResponse> estoqueResponseList = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueResponse response = estoqueResponseToEntity.mapper(estoque);
            estoqueResponseList.add(response);
        }

        return estoqueResponseList;
    }

    public EstoqueResponse update(UUID idEstoque, EstoqueRequest estoqueRequest) {
        try {
            Estoque estoque = estoqueRepository.findById(idEstoque).orElseThrow(EstoqueNotFoundException::new);

            // Atualize as propriedades específicas do Estoque
            estoque.setLocalizacao(estoqueRequest.getLocalizacao());
            estoque.setAndar(estoqueRequest.getAndar());
            estoque.setFila(estoqueRequest.getFila());
            estoque.setRua(estoqueRequest.getRua());
            estoque.setPrateleira(estoqueRequest.getPrateleira());
            estoqueRepository.save(estoque);

            EstoqueResponse estoqueResponse = new EstoqueResponse();
            BeanUtils.copyProperties(estoque, estoqueResponse);

            return estoqueResponse;
        } catch (RuntimeException notFoundException) {
            throw new EstoqueNotFoundException();
        }
    }

    public void delete(UUID idEstoque) {
        try {
            estoqueRepository.findById(idEstoque);
            estoqueRepository.deleteById(idEstoque);
        } catch (EstoqueNotFoundException notFoundException) {
            throw new EstoqueNotFoundException();
        }
    }
}
