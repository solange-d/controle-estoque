package com.controleestoque.controller;

import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.model.FornecedorResponse;
import com.controleestoque.service.FornecedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/fornecedor")
public class FornecedorController {
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<UUID> createFornecedor(@RequestBody FornecedorRequest fornecedor){
        UUID idFornecedor = fornecedorService.create(fornecedor);
        return new ResponseEntity<>(idFornecedor, HttpStatus.CREATED);
    }

    @GetMapping("/{idFornecedor}")
    public ResponseEntity<FornecedorResponse> getFornecedor(@PathVariable UUID idFornecedor){
        var RequestedFornecedor = fornecedorService.getFornecedorById(idFornecedor);
        return new ResponseEntity<>(RequestedFornecedor, HttpStatus.OK);
    }


    @GetMapping("/fornecedores")
    public List<FornecedorResponse> getAll(){
        return fornecedorService.getAll();
    }


    @PutMapping("/{idFornecedor}")
    public ResponseEntity<FornecedorResponse> updateFornecedor(@PathVariable UUID idFornecedor,
                                                                     @RequestBody FornecedorRequest fornecedorRequest) {
        fornecedorService.update(idFornecedor, fornecedorRequest);
        return new ResponseEntity(fornecedorRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{idFornecedor}")
    public ResponseEntity<FornecedorResponse> deleteFornecedor(@PathVariable UUID idFornecedor) {
        fornecedorService.delete(idFornecedor);
        return new ResponseEntity(HttpStatus.OK);
    }

}