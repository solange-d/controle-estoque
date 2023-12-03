package com.controleestoque.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.model.ItemEstoqueRequest;
import com.controleestoque.model.ItemEstoqueResponse;
import com.controleestoque.service.ItemEstoqueService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/itemEstoque")
public class ItemEstoqueController {
    private ItemEstoqueService itemEstoqueService;
    
    @GetMapping("/itensEstoque")
    public List<ItemEstoqueResponse> getAll() {
        return itemEstoqueService.getAll();
    }

    @PutMapping("/{idItemEstoque}")
    public ResponseEntity<ItemEstoqueResponse> updateItemEstoque(@PathVariable UUID idItemEstoque,
                                                         @RequestBody ItemEstoqueRequest itemEstoqueRequest) {
        itemEstoqueService.update(idItemEstoque, itemEstoqueRequest);
        return new ResponseEntity<ItemEstoqueResponse>(itemEstoqueService.getItemEstoqueById(idItemEstoque), HttpStatus.OK);
    }

    @DeleteMapping("/{idItemEstoque}")
    public ResponseEntity<ItemEstoqueResponse> deleteItemEstoque(@PathVariable UUID idItemEstoque) {
        itemEstoqueService.delete(idItemEstoque);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createItemEstoque(@RequestBody ItemEstoqueRequest itemEstoqueRequest) {
        UUID idItemEstoque = itemEstoqueService.create(itemEstoqueRequest);
        return new ResponseEntity<>(idItemEstoque, HttpStatus.CREATED);
    }

    @GetMapping("/{idItemEstoque}")
    public ResponseEntity<ItemEstoqueResponse> getItemEstoque(@PathVariable UUID idItemEstoque) {
        var requestedItemEstoque = itemEstoqueService.getItemEstoqueById(idItemEstoque);
        return new ResponseEntity<>(requestedItemEstoque, HttpStatus.OK);
    }

}