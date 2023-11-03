package com.controleestoque.controller;

import com.controleestoque.exceptions.ProdutoNotFoundException;
import com.controleestoque.model.ProdutoRequest;
import com.controleestoque.model.ProdutoResponse;
import com.controleestoque.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/produto")
public class ProdutoController {

    private ProdutoService produtoService;

    @PostMapping("{idFornecedor}")
    public ResponseEntity<UUID> createProduto(@PathVariable UUID idFornecedor,
                                              @RequestBody ProdutoRequest produtoRequest) {
        try {
            UUID produtoId = produtoService.create(idFornecedor, produtoRequest);
            return new ResponseEntity<>(produtoId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("todos/{idFornecedor}")
    public ResponseEntity<List<ProdutoResponse>> getAll(@PathVariable UUID idFornecedor){
        try {
            List<ProdutoResponse> response = produtoService.getAll(idFornecedor);
            return ResponseEntity.ok(response);
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
