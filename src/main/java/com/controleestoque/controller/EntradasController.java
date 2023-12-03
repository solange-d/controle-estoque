package com.controleestoque.controller;

import com.controleestoque.model.EntradaRequest;
import com.controleestoque.model.EntradaResponse;
import com.controleestoque.service.EntradaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/entrada")
public class EntradasController {
    private final EntradaService entradaService;

    @PostMapping
    public ResponseEntity<UUID> createEntrada(@RequestBody EntradaRequest entradaRequest) {
        UUID idEntrada = entradaService.create(entradaRequest);
        return new ResponseEntity<>(idEntrada, HttpStatus.CREATED);
    }

    @GetMapping("/{idEntrada}")
    public ResponseEntity<EntradaResponse> getEntrada(@PathVariable UUID idEntrada) {
        var requestedEntrada = entradaService.getEntradaById(idEntrada);
        return new ResponseEntity<>(requestedEntrada, HttpStatus.OK);
    }

    @GetMapping("/entradas")
    public List<EntradaResponse> getAll() {
        return entradaService.getAll();
    }

    @PutMapping("/{idEntrada}")
    public ResponseEntity<EntradaResponse> updateEntrada(@PathVariable UUID idEntrada,
                                                         @RequestBody EntradaRequest entradaRequest) {
        entradaService.update(idEntrada, entradaRequest);
        return new ResponseEntity<>(entradaService.getEntradaById(idEntrada), HttpStatus.OK);
    }

    @DeleteMapping("/{idEntrada}")
    public ResponseEntity<EntradaResponse> deleteEntrada(@PathVariable UUID idEntrada) {
        entradaService.delete(idEntrada);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
