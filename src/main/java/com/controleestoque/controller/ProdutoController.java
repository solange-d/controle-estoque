package com.controleestoque.controller;

import com.controleestoque.entity.Produto;
import com.controleestoque.exceptions.FornecedorNotFoundException;
import com.controleestoque.exceptions.ProdutoNotFoundException;
import com.controleestoque.model.EnderecoRequest;
import com.controleestoque.model.EnderecoResponse;
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
    public ResponseEntity<List<ProdutoResponse>> getAll(@PathVariable UUID idFornecedor) {
        try {
            List<ProdutoResponse> response = produtoService.getAll(idFornecedor);
            return ResponseEntity.ok(response);
        } catch (ProdutoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponse> getProduto(@PathVariable UUID idProduto) {
        var produtoPesquisado = produtoService.getProdutoById(idProduto);
        return new ResponseEntity<>(produtoPesquisado, HttpStatus.OK);
    }


    @GetMapping("/nome/{nome}")
    public List<ProdutoResponse> getProdutosByName(@PathVariable String nome) {
        return produtoService.getProdutosByName(nome);
    }

    @PutMapping("/fornecedor/{idFornecedor}/produto/{idProduto}")
    public ResponseEntity<ProdutoResponse> updateProdutoFornecedor(@PathVariable UUID idFornecedor,
                                                                     @PathVariable UUID idProduto,
                                                                     @RequestBody ProdutoRequest produto) {
        produtoService.update(idFornecedor, idProduto, produto);
        return new ResponseEntity(produto, HttpStatus.OK);
    }


    @DeleteMapping("/fornecedor/{idFornecedor}/produto/{idProduto}")
    public ResponseEntity<ProdutoResponse> deleteProdutoFornecedor(@PathVariable UUID idFornecedor, @PathVariable UUID idProduto) {
        produtoService.delete(idFornecedor, idProduto);
        return new ResponseEntity(HttpStatus.OK);
    }


}




