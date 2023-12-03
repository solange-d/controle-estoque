package com.controleestoque.service;

import com.controleestoque.entity.Saida;
import com.controleestoque.exceptions.SaidaNotFoundException;
import com.controleestoque.mapper.SaidaRequestToEntity;
import com.controleestoque.mapper.SaidaResponseToEntity;
import com.controleestoque.model.SaidaRequest;
import com.controleestoque.model.SaidaResponse;
import com.controleestoque.repository.SaidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaidaService {

    private final SaidaRepository saidaRepository;
    private final SaidaRequestToEntity saidaRequestToEntity;
    private final SaidaResponseToEntity saidaResponseToEntity;

    public UUID create(SaidaRequest saidaRequest) {
        Saida saida = saidaRequestToEntity.mapper(saidaRequest);
        Saida savedSaida = saidaRepository.save(saida);
        return savedSaida.getIdSaida();
    }

    public SaidaResponse getSaidaById(UUID idSaida) {
        Saida saida = saidaRepository.findById(idSaida)
                .orElseThrow(SaidaNotFoundException::new);
        return saidaResponseToEntity.mapper(saida);
    }

    public List<SaidaResponse> getAll() {
        List<Saida> saidas = (List<Saida>) saidaRepository.findAll();
        return saidas.stream().map(saidaResponseToEntity::mapper).collect(Collectors.toList());
    }

    public void update(UUID idSaida, SaidaRequest saidaRequest) {
        Saida existingSaida = saidaRepository.findById(idSaida)
                .orElseThrow(SaidaNotFoundException::new);

        BeanUtils.copyProperties(saidaRequest, existingSaida);
        saidaRepository.save(existingSaida);
    }

    public void delete(UUID idSaida) {
        Saida saida = saidaRepository.findById(idSaida)
                .orElseThrow(SaidaNotFoundException::new);

        saidaRepository.delete(saida);
    }

}
