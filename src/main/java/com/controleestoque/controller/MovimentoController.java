package com.controleestoque.controller;

import com.controleestoque.model.MovimentoRequest;
import com.controleestoque.model.MovimentoResponse;
import com.controleestoque.service.MovimentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movimento")
public class MovimentoController {
    private final MovimentoService movimentoService;

    @PostMapping
    public ResponseEntity<UUID> createMovimento(@RequestBody MovimentoRequest movimentoRequest) {
        UUID idMovimento = movimentoService.create(movimentoRequest);
        return new ResponseEntity<>(idMovimento, HttpStatus.CREATED);
    }

    @GetMapping("/{idMovimento}")
    public ResponseEntity<MovimentoResponse> getMovimento(@PathVariable UUID idMovimento) {
        var requestedMovimento = movimentoService.getMovimentoById(idMovimento);
        return new ResponseEntity<>(requestedMovimento, HttpStatus.OK);
    }

    @GetMapping("/movimentos")
    public List<MovimentoResponse> getAll() {
        return movimentoService.getAll();
    }

    @PutMapping("/{idMovimento}")
    public ResponseEntity<MovimentoResponse> updateMovimento(@PathVariable UUID idMovimento,
                                                             @RequestBody MovimentoRequest movimentoRequest) {
        movimentoService.update(idMovimento, movimentoRequest);
        return new ResponseEntity<>(movimentoService.getMovimentoById(idMovimento), HttpStatus.OK);
    }

    @DeleteMapping("/{idMovimento}")
    public ResponseEntity<MovimentoResponse> deleteMovimento(@PathVariable UUID idMovimento) {
        movimentoService.delete(idMovimento);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
