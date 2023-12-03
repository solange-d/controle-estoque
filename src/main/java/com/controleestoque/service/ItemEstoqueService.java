package com.controleestoque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.controleestoque.entity.ItemEstoque;
import com.controleestoque.exceptions.ItemEstoqueNotFoundException;
import com.controleestoque.mapper.ItemEstoqueRequestToEntity;
import com.controleestoque.mapper.ItemEstoqueResponseToEntity;
import com.controleestoque.model.ItemEstoqueRequest;
import com.controleestoque.model.ItemEstoqueResponse;
import com.controleestoque.repository.ItemEstoqueRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemEstoqueService {
    private ItemEstoqueRequestToEntity itemEstoqueRequestToEntity;
    private ItemEstoqueResponseToEntity itemEstoqueResponseToEntity;
    private ItemEstoqueRepository itemEstoqueRepository;

    public UUID create(ItemEstoqueRequest itemEstoqueRequest) {
        ItemEstoque itemEstoque = itemEstoqueRequestToEntity.mapper(itemEstoqueRequest);
        ItemEstoque savedItemEstoque = itemEstoqueRepository.save(itemEstoque);
        return savedItemEstoque.getIdItemEstoque();
    }

    public ItemEstoqueResponse getItemEstoqueById(UUID idItemEstoque) {
        ItemEstoque itemEstoque = itemEstoqueRepository.findById(idItemEstoque).orElseThrow(ItemEstoqueNotFoundException::new);
        ItemEstoqueResponse response = itemEstoqueResponseToEntity.mapper(itemEstoque);
        return response;
    }

    public List<ItemEstoqueResponse> getAll() {
        var itemEstoques = itemEstoqueRepository.findAll();
        List<ItemEstoqueResponse> itemEstoqueResponseList = new ArrayList<>();

        for (ItemEstoque itemEstoque : itemEstoques) {
            ItemEstoqueResponse response = itemEstoqueResponseToEntity.mapper(itemEstoque);
            itemEstoqueResponseList.add(response);
        }

        return itemEstoqueResponseList;
    }

    public ItemEstoqueResponse update(UUID idItemEstoque, ItemEstoqueRequest itemEstoqueRequest) {
        try {
            ItemEstoque itemEstoque = itemEstoqueRepository.findById(idItemEstoque).orElseThrow(ItemEstoqueNotFoundException::new);

            // Atualize as propriedades específicas do ItemEstoque
            itemEstoque.getEstoque().setIdEstoque(itemEstoqueRequest.getIdItemEstoque());
            itemEstoque.getProduto().setIdProduto(itemEstoqueRequest.getIdProduto());
            itemEstoque.getEntrada().setIdEntrada(itemEstoqueRequest.getIdEntrada());
            itemEstoque.getSaida().setIdSaida(itemEstoqueRequest.getIdSaida());
            itemEstoqueRepository.save(itemEstoque);

            ItemEstoqueResponse itemEstoqueResponse = new ItemEstoqueResponse();
            BeanUtils.copyProperties(itemEstoque, itemEstoqueResponse);

            return itemEstoqueResponse;
        } catch (RuntimeException notFoundException) {
            throw new ItemEstoqueNotFoundException();
        }
    }

    public void delete(UUID idItemEstoque) {
        try {
            itemEstoqueRepository.findById(idItemEstoque);
            itemEstoqueRepository.deleteById(idItemEstoque);
        } catch (ItemEstoqueNotFoundException notFoundException) {
            throw new ItemEstoqueNotFoundException();
        }
    }
}
