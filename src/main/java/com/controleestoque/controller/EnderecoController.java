package com.controleestoque.controller;

import com.controleestoque.entity.Endereco;
import com.controleestoque.model.EnderecoRequest;
import com.controleestoque.model.EnderecoResponse;
import com.controleestoque.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/endereco")
public class EnderecoController {
    private EnderecoService enderecoService;


    @PostMapping("/fornecedor/{idFornecedor}")
    public ResponseEntity<UUID> createEnderecoFornecedor(@PathVariable UUID idFornecedor, @RequestBody EnderecoRequest endereco){
        UUID response = enderecoService.createEnderecoFornecedor(idFornecedor, endereco);
        return new ResponseEntity<UUID>(response, HttpStatus.CREATED);
    }

    @GetMapping("/fornecedor/{idFornecedor}")
    public List<Endereco> getEnderecosFornecedor(@PathVariable UUID idFornecedor) {
        return enderecoService.getEnderecosByFornecedorId(idFornecedor);
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<Endereco> getEndereco(@PathVariable UUID idEndereco){
        var RequestedEndereco = enderecoService.getEnderecoById(idEndereco);
        return new ResponseEntity<>(RequestedEndereco, HttpStatus.OK);
    }


    @PutMapping("/fornecedor/{idFornecedor}/{idEndereco}")
    public ResponseEntity<EnderecoResponse> updateEnderecoFornecedor(@PathVariable UUID idFornecedor,
                                                                     @PathVariable UUID idEndereco,
                                                                     @RequestBody EnderecoRequest endereco) {
        enderecoService.update(idFornecedor, idEndereco, endereco);
        return new ResponseEntity(endereco, HttpStatus.OK);
    }

    @DeleteMapping("/fornecedor/{idFornecedor}/{idEndereco}")
    public ResponseEntity<EnderecoResponse> deleteEnderecoFornecedor(@PathVariable UUID idFornecedor, @PathVariable UUID idEndereco) {
        enderecoService.delete(idFornecedor, idEndereco);
        return new ResponseEntity(HttpStatus.OK);
    }


}
