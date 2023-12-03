package com.controleestoque.service;

import com.controleestoque.entity.Movimento;
import com.controleestoque.exceptions.MovimentoNotFoundException;
import com.controleestoque.mapper.MovimentoRequestToEntity;
import com.controleestoque.mapper.MovimentoResponseToEntity;
import com.controleestoque.model.MovimentoRequest;
import com.controleestoque.model.MovimentoResponse;
import com.controleestoque.repository.MovimentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovimentoService {

    private final MovimentoRepository movimentoRepository;
    private final MovimentoRequestToEntity movimentoRequestToEntity;
    private final MovimentoResponseToEntity movimentoResponseToEntity;

    public UUID create(MovimentoRequest movimentoRequest) {
        Movimento movimento = movimentoRequestToEntity.mapper(movimentoRequest);
        Movimento savedMovimento = movimentoRepository.save(movimento);
        return savedMovimento.getIdMovimento();
    }

    public MovimentoResponse getMovimentoById(UUID idMovimento) {
        Movimento movimento = movimentoRepository.findById(idMovimento)
                .orElseThrow(MovimentoNotFoundException::new);
        return mapMovimentoToResponse(movimento);
    }

    public List<MovimentoResponse> getAll() {
        List<Movimento> movimentos = (List<Movimento>) movimentoRepository.findAll();
        return movimentos.stream().map(movimentoResponseToEntity::mapper).collect(Collectors.toList());
    }

    public void update(UUID idMovimento, MovimentoRequest movimentoRequest) {
        Movimento existingMovimento = movimentoRepository.findById(idMovimento)
                .orElseThrow(MovimentoNotFoundException::new);

        BeanUtils.copyProperties(movimentoRequest, existingMovimento);
        movimentoRepository.save(existingMovimento);
    }

    public void delete(UUID idMovimento) {
        Movimento movimento = movimentoRepository.findById(idMovimento)
                .orElseThrow(MovimentoNotFoundException::new);

        movimentoRepository.delete(movimento);
    }

    private MovimentoResponse mapMovimentoToResponse(Movimento movimento) {
        MovimentoResponse movimentoResponse = new MovimentoResponse();
        BeanUtils.copyProperties(movimento, movimentoResponse);
        return movimentoResponse;
    }
}
