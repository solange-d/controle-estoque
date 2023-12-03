package com.controleestoque.controller;

import com.controleestoque.model.SaidaRequest;
import com.controleestoque.model.SaidaResponse;
import com.controleestoque.service.SaidaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/saida")
public class SaidaController {
    private final SaidaService saidaService;

    @PostMapping
    public ResponseEntity<UUID> createSaida(@RequestBody SaidaRequest saidaRequest) {
        UUID idSaida = saidaService.create(saidaRequest);
        return new ResponseEntity<>(idSaida, HttpStatus.CREATED);
    }

    @GetMapping("/{idSaida}")
    public ResponseEntity<SaidaResponse> getSaida(@PathVariable UUID idSaida) {
        var requestedSaida = saidaService.getSaidaById(idSaida);
        return new ResponseEntity<>(requestedSaida, HttpStatus.OK);
    }

    @GetMapping("/saidas")
    public List<SaidaResponse> getAll() {
        return saidaService.getAll();
    }

    @PutMapping("/{idSaida}")
    public ResponseEntity<SaidaResponse> updateSaida(@PathVariable UUID idSaida,
                                                     @RequestBody SaidaRequest saidaRequest) {
        saidaService.update(idSaida, saidaRequest);
        return new ResponseEntity<>(saidaService.getSaidaById(idSaida), HttpStatus.OK);
    }

    @DeleteMapping("/{idSaida}")
    public ResponseEntity<SaidaResponse> deleteSaida(@PathVariable UUID idSaida) {
        saidaService.delete(idSaida);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
