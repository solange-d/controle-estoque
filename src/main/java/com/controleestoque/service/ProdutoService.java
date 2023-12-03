package com.controleestoque.service;

import com.controleestoque.entity.Produto;
import com.controleestoque.exceptions.EnderecoNotFoundException;
import com.controleestoque.exceptions.ProdutoNotFoundException;
import com.controleestoque.mapper.ProdutoRequestToEntity;
import com.controleestoque.mapper.ProdutoResponseToEntity;
import com.controleestoque.model.ProdutoRequest;
import com.controleestoque.model.ProdutoResponse;
import com.controleestoque.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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


    public List<ProdutoResponse> getProdutosByName(String nome) {
        var produtos = produtoRepository.findProdutosByNome(nome);
        List<ProdutoResponse> produtoResponseList = new ArrayList<>();
        for(Produto produto: produtos){
            ProdutoResponse response = produtoResponseToEntity.mapper(produto);
            produtoResponseList.add(response);
        }
        return produtoResponseList;
    }


    public Produto getProdutoByIdEntity(UUID idProduto){
        Optional<Produto> produto = produtoRepository.findById(idProduto);
        return produto.orElseThrow(ProdutoNotFoundException::new);
    }



    public ProdutoResponse update(UUID idFornecedor, UUID idProduto, ProdutoRequest produtoRequest) {
        try {
            Produto produto = getProdutoByIdEntity(idProduto);
            BeanUtils.copyProperties(produtoRequest, produto);
            produto.setMarca(produtoRequest.getMarca());
            produto.setNome(produtoRequest.getNome());
            produto.setDescricao(produtoRequest.getDescricao());
            produto.setEan(produtoRequest.getEan());
            produto.setAltura(produtoRequest.getAltura());
            produto.setLargura(produtoRequest.getLargura());
            produto.setComprimento(produtoRequest.getComprimento());
            produto.setPeso(produtoRequest.getPeso());
            produtoRepository.save(produto);
            var produtoResponse = new ProdutoResponse();
            BeanUtils.copyProperties(produto, produtoResponse);
            return produtoResponse;
        } catch (RuntimeException notRuntimeException) {
            throw new ProdutoNotFoundException();
        }
    }

    public void delete(UUID idFornecedor, UUID idProduto) {
        try {
            getProdutoByIdEntity(idProduto);
            produtoRepository.deleteById(idProduto);
        } catch (RuntimeException notFoundException) {
            throw new EnderecoNotFoundException();
        }

    }

}
