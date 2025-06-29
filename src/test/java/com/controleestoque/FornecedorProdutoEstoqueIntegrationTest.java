package com.controleestoque;

import com.controleestoque.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FornecedorProdutoEstoqueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(8)
    public void deveIncluirItemEstoqueComSucesso_CT08() throws Exception {
        // 1. Criar fornecedor
        FornecedorRequest fornecedor = new FornecedorRequest();
        fornecedor.setNome("Fornecedor CT08");
        fornecedor.setCnpj("78945612000188");
        fornecedor.setEmail("fornecedor.ct08@empresa.com");
        fornecedor.setTelefone("48999111222");

        MvcResult fornecedorResult = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID fornecedorId = UUID.fromString(fornecedorResult.getResponse().getContentAsString());

        // 2. Criar produto
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome("Webcam CT08");
        produto.setMarca("Logitech");
        produto.setDescricao("Webcam Full HD 1080p");
        produto.setEan("7894567890123");
        produto.setAltura(5.0);
        produto.setLargura(8.0);
        produto.setComprimento(8.0);
        produto.setPeso(0.4);

        MvcResult produtoResult = mockMvc.perform(post("/api/produto/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID produtoId = UUID.fromString(produtoResult.getResponse().getContentAsString());

        // 3. Criar estoque
        EstoqueRequest estoque = new EstoqueRequest();
        estoque.setLocalizacao("Bloco D");
        estoque.setAndar("2º");
        estoque.setFila("F4");
        estoque.setRua("R5");
        estoque.setPrateleira("P12");

        MvcResult estoqueResult = mockMvc.perform(post("/api/estoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estoque)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID estoqueId = UUID.fromString(estoqueResult.getResponse().getContentAsString());

        // 4. Criar entrada
        EntradaRequest entrada = new EntradaRequest();
        entrada.setDataEntrada(LocalDate.of(2025, 7, 1));
        entrada.setCustoAquisicao(new BigDecimal("199.90"));
        entrada.setIdUsuario(UUID.randomUUID()); // ajustar com usuário real se necessário

        MvcResult entradaResult = mockMvc.perform(post("/api/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID entradaId = UUID.fromString(entradaResult.getResponse().getContentAsString());

        // 5. Criar item de estoque
        ItemEstoqueRequest item = new ItemEstoqueRequest();
        item.setIdProduto(produtoId);
        item.setIdEstoque(estoqueId);
        item.setIdEntrada(entradaId);
        item.setIdSaida(null);

        MvcResult itemResult = mockMvc.perform(post("/api/itemestoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID idItemEstoque = UUID.fromString(itemResult.getResponse().getContentAsString());
        System.out.println("📦 Item de estoque criado com sucesso: " + idItemEstoque);

        // 6. Verificar com GET que o item foi armazenado
        MvcResult getResult = mockMvc.perform(get("/api/itemestoque/" + idItemEstoque))
                .andExpect(status().isOk())
                .andReturn();

        String json = getResult.getResponse().getContentAsString();
        System.out.println("🔎 Detalhes do item de estoque: " + json);

        assertThat(json).contains(produto.getNome());
        assertThat(json).contains("Bloco D");
        assertThat(json).contains("199.90");
    }

    @Test
    @Order(9)
    public void deveAtualizarStatusParaEstocado_CT09() throws Exception {
        // 1. Criar fornecedor
        FornecedorRequest fornecedor = new FornecedorRequest();
        fornecedor.setNome("Fornecedor CT05");
        fornecedor.setCnpj("99999999000188");
        fornecedor.setEmail("fornecedorct05@example.com");
        fornecedor.setTelefone("48988885555");

        MvcResult fornecedorResult = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID fornecedorId = UUID.fromString(fornecedorResult.getResponse().getContentAsString());

        // Cria o request do produto
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome("HD Externo");
        produto.setMarca("Seagate");
        produto.setDescricao("HD 2TB USB 3.0");
        produto.setEan("7891234567033");
        produto.setAltura(2.0);
        produto.setLargura(8.0);
        produto.setComprimento(12.0);
        produto.setPeso(0.3);

        MvcResult produtoResult = mockMvc.perform(post("/api/produto/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID produtoId = UUID.fromString(produtoResult.getResponse().getContentAsString());

        // 4. Criar estoque
        EstoqueRequest estoque = new EstoqueRequest();
        estoque.setLocalizacao("Setor C");
        estoque.setAndar("1º");
        estoque.setFila("F2");
        estoque.setRua("R1");
        estoque.setPrateleira("P3");

        MvcResult estoqueResult = mockMvc.perform(post("/api/estoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estoque)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID estoqueId = UUID.fromString(estoqueResult.getResponse().getContentAsString());

        // 2. Criar usuário
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("Usuário CT05");
        usuario.setEmail("usuarioct05@example.com");
        usuario.setSenha("segura123");

        MvcResult usuarioResult = mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID usuarioId = UUID.fromString(usuarioResult.getResponse().getContentAsString());

        // 5. Criar entrada
        EntradaRequest entrada = new EntradaRequest();
        entrada.setDataEntrada(LocalDate.of(2025, 7, 1));
        entrada.setCustoAquisicao(BigDecimal.valueOf(350.00));
        entrada.setIdUsuario(usuarioId);

        MvcResult entradaResult = mockMvc.perform(post("/api/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID entradaId = UUID.fromString(entradaResult.getResponse().getContentAsString());

        // [Reaproveitar fluxo de criação de fornecedor, produto, estoque, entrada...]

        // 1. Criar o item de estoque
        ItemEstoqueRequest itemEstoque = new ItemEstoqueRequest();
        itemEstoque.setIdProduto(produtoId);
        itemEstoque.setIdEstoque(estoqueId);
        itemEstoque.setIdEntrada(entradaId);
        itemEstoque.setIdSaida(null); // ainda estocado

        MvcResult itemResult = mockMvc.perform(post("/api/itemestoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemEstoque)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID idItemEstoque = UUID.fromString(itemResult.getResponse().getContentAsString());

        // 2. Consultar o item criado (assumindo endpoint GET /api/itemestoque/{id})
        MvcResult getResult = mockMvc.perform(get("/api/itemestoque/" + idItemEstoque))
                .andExpect(status().isOk())
                .andReturn();

        String json = getResult.getResponse().getContentAsString();
        System.out.println("🔎 Conteúdo retornado: " + json);

        // 3. Validar que o status retornado está como "ESTOCADO"
        assertThat(json).contains("ESTOCADO");
    }

}
