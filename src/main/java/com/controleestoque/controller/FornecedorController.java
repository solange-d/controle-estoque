package com.controleestoque.controller;

import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.service.FornecedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
