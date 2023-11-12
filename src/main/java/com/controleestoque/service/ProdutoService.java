package com.controleestoque.service;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.entity.Produto;
import com.controleestoque.exceptions.ProdutoNotFoundException;
import com.controleestoque.mapper.ProdutoRequestToEntity;
import com.controleestoque.mapper.ProdutoResponseToEntity;
import com.controleestoque.model.ProdutoRequest;
import com.controleestoque.model.ProdutoResponse;
import com.controleestoque.repository.FornecedorRepository;
import com.controleestoque.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProdutoService {
    private ProdutoRepository produtoRepository;
    private FornecedorService fornecedorService;
    private ProdutoRequestToEntity produtoRequestToEntity;
    private ProdutoResponseToEntity produtoResponseToEntity;
    private FornecedorRepository fornecedorRepository;


    public ProdutoResponse getProdutoById(UUID idProduto){
        Optional<Produto> produto = produtoRepository.findById(idProduto);
        if(produto.isEmpty()){
            throw new ProdutoNotFoundException();
        }
        ProdutoResponse response = produtoResponseToEntity.mapper(produto.get());
        return response;
    }

    public UUID create(UUID idFornecedor, ProdutoRequest produtoRequest){
        var fornecedor = fornecedorService.getById(idFornecedor);
        Produto produto = produtoRequestToEntity.mapper(produtoRequest);

        if (fornecedor.getProdutos() == null) {
            fornecedor.setProdutos(new ArrayList<>());
        }

        if (produto.getFornecedores() == null) {
            produto.setFornecedores(new ArrayList<>());
        }

        fornecedor.getProdutos().add(produto);
        produto.getFornecedores().add(fornecedor);

        var produtoPersistido = produtoRepository.save(produto);
        return produtoPersistido.getIdProduto();
    }

    public List<ProdutoResponse> getAll(UUID idFornecedor){
        var produtos = produtoRepository.findAllProdutosByFornecedor(idFornecedor);
        List<ProdutoResponse> produtoResponseList = new ArrayList<>();
        for(Produto produto: produtos){
            ProdutoResponse response = produtoResponseToEntity.mapper(produto);
            produtoResponseList.add(response);
        }
        return produtoResponseList;
    }


}
