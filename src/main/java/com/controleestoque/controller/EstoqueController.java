package com.controleestoque.controller;

import com.controleestoque.entity.Estoque;
import com.controleestoque.model.EstoqueRequest;
import com.controleestoque.model.EstoqueResponse;
import com.controleestoque.service.EstoqueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/estoque")
public class EstoqueController {
    private EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<UUID> createEstoque(@RequestBody EstoqueRequest estoqueRequest) {
        UUID idEstoque = estoqueService.create(estoqueRequest);
        return new ResponseEntity<>(idEstoque, HttpStatus.CREATED);
    }

    @GetMapping("/{idEstoque}")
    public ResponseEntity<EstoqueResponse> getEstoque(@PathVariable UUID idEstoque) {
        var requestedEstoque = estoqueService.getEstoqueById(idEstoque);
        return new ResponseEntity<>(requestedEstoque, HttpStatus.OK);
    }

    @GetMapping("/estoques")
    public List<EstoqueResponse> getAll() {
        return estoqueService.getAll();
    }

    @PutMapping("/{idEstoque}")
    public ResponseEntity<EstoqueResponse> updateEstoque(@PathVariable UUID idEstoque,
                                                         @RequestBody EstoqueRequest estoqueRequest) {
        estoqueService.update(idEstoque, estoqueRequest);
        return new ResponseEntity<EstoqueResponse>(estoqueService.getEstoqueById(idEstoque), HttpStatus.OK);
    }

    @DeleteMapping("/{idEstoque}")
    public ResponseEntity<EstoqueResponse> deleteEstoque(@PathVariable UUID idEstoque) {
        estoqueService.delete(idEstoque);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
