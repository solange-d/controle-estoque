package com.controleestoque;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.entity.Produto;
import com.controleestoque.mapper.ProdutoRequestToEntity;
import com.controleestoque.mapper.ProdutoResponseToEntity;
import com.controleestoque.model.ProdutoRequest;
import com.controleestoque.model.ProdutoResponse;
import com.controleestoque.repository.ProdutoRepository;
import com.controleestoque.service.FornecedorService;
import com.controleestoque.service.ProdutoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoResponseToEntity produtoResponseToEntity;

    @Mock
    private ProdutoRequestToEntity produtoRequestToEntity;

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private FornecedorService fornecedorService;

    @Test
    public void deveCadastrarProdutoComSucesso_CT11() {
        // Arrange
        UUID idFornecedor = UUID.randomUUID();

        ProdutoRequest request = new ProdutoRequest();
        request.setMarca("Agratto");
        request.setNome("Ar Condicionado");
        request.setDescricao("Especificações técnicas");
        request.setEan("ABC123");
        request.setAltura(0.30);
        request.setLargura(1.0);
        request.setComprimento(0.20);
        request.setPeso(10.0);

        Produto produto = new Produto();
        UUID idProduto = UUID.randomUUID();
        produto.setIdProduto(idProduto);
        produto.setMarca("Agratto");
        produto.setNome("Ar Condicionado");
        produto.setDescricao("Especificações técnicas");
        produto.setEan("ABC123");
        produto.setAltura(0.30);
        produto.setLargura(1.0);
        produto.setComprimento(0.20);
        produto.setPeso(10.0);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(idFornecedor);
        fornecedor.setProdutos(new ArrayList<>());

        when(fornecedorService.getById(idFornecedor)).thenReturn(fornecedor);
        when(produtoRequestToEntity.mapper(request)).thenReturn(produto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // Act
        UUID resultado = produtoService.create(idFornecedor, request);

        // Assert
        assertEquals(idProduto, resultado);

        verify(produtoRepository).save(argThat(p -> p.getMarca().equals("Agratto") &&
                p.getNome().equals("Ar Condicionado") &&
                p.getDescricao().equals("Especificações técnicas") &&
                p.getEan().equals("ABC123") &&
                Double.compare(p.getAltura(), 0.30) == 0 &&
                Double.compare(p.getLargura(), 1.0) == 0 &&
                Double.compare(p.getComprimento(), 0.20) == 0 &&
                Double.compare(p.getPeso(), 10.0) == 0));
    }

    @Test
    public void deveLancarExcecaoAoCadastrarProdutoComEanDuplicado_CT12() {
        // Arrange
        UUID idFornecedor = UUID.randomUUID();

        ProdutoRequest request = new ProdutoRequest();
        request.setMarca("Agratto");
        request.setNome("Ar Condicionado");
        request.setDescricao("Especificações técnicas");
        request.setEan("ABC123");
        request.setAltura(0.30);
        request.setLargura(1.0);
        request.setComprimento(0.20);
        request.setPeso(10.0);

        // Produto já existente com o mesmo EAN
        Produto produtoExistente = new Produto();
        produtoExistente.setEan("ABC123");

        when(produtoRepository.findByEan("ABC123")).thenReturn(Optional.of(produtoExistente));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            produtoService.create(idFornecedor, request);
        });

        verify(produtoRepository, never()).save(any());
    }

    @Test
    public void deveBuscarProdutosPorNomeExato_CT13() {
        // Arrange
        String nomeBuscado = "Ar Condicionado";

        Produto produto1 = new Produto();
        produto1.setNome("Ar Condicionado");

        List<Produto> produtosEncontrados = List.of(produto1);

        when(produtoRepository.findProdutosByNome(nomeBuscado)).thenReturn(produtosEncontrados);

        ProdutoResponse responseSimulado = new ProdutoResponse();
        responseSimulado.setNome("Ar Condicionado");

        when(produtoResponseToEntity.mapper(produto1)).thenReturn(responseSimulado);

        // Act
        List<ProdutoResponse> resultado = produtoService.getProdutosByName(nomeBuscado);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Ar Condicionado", resultado.get(0).getNome());

        verify(produtoRepository).findProdutosByNome(nomeBuscado);
    }

}
