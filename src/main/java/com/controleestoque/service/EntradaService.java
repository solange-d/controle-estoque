package com.controleestoque.service;

import com.controleestoque.entity.Entrada;
import com.controleestoque.exceptions.EntradaNotFoundException;
import com.controleestoque.mapper.EntradaRequestToEntity;
import com.controleestoque.mapper.EntradaResponseToEntity;
import com.controleestoque.model.EntradaRequest;
import com.controleestoque.model.EntradaResponse;
import com.controleestoque.repository.EntradaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final EntradaRequestToEntity entradaRequestToEntity;
    private final EntradaResponseToEntity entradaResponseToEntity;

    public UUID create(EntradaRequest entradaRequest) {
        Entrada entrada = entradaRequestToEntity.mapper(entradaRequest);
        Entrada savedEntrada = entradaRepository.save(entrada);
        return savedEntrada.getIdEntrada();
    }

    public EntradaResponse getEntradaById(UUID idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(EntradaNotFoundException::new);

        return entradaResponseToEntity.mapper(entrada);
    }

    public List<EntradaResponse> getAll() {
        List<Entrada> entradas = (List<Entrada>) entradaRepository.findAll();
        return entradas.stream().map(entradaResponseToEntity::mapper).collect(Collectors.toList());
    }

    public void update(UUID idEntrada, EntradaRequest entradaRequest) {
        Entrada existingEntrada = entradaRepository.findById(idEntrada)
                .orElseThrow(EntradaNotFoundException::new);

        BeanUtils.copyProperties(entradaRequest, existingEntrada);
        entradaRepository.save(existingEntrada);
    }

    public void delete(UUID idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(EntradaNotFoundException::new);

        entradaRepository.delete(entrada);
    }

}
