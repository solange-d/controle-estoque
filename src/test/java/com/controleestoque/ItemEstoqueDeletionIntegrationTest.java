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
public class ItemEstoqueDeletionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(10)
    public void deveRemoverItemEstoque_CT10() throws Exception {
        // 1. Criar fornecedor
        FornecedorRequest fornecedor = new FornecedorRequest();
        fornecedor.setNome("Fornecedor CT10");
        fornecedor.setCnpj("11223344000199");
        fornecedor.setEmail("ct10@fornecedor.com");
        fornecedor.setTelefone("48988776655");

        UUID fornecedorId = UUID.fromString(
                mockMvc.perform(post("/api/fornecedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fornecedor)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        // 2. Criar produto
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome("CT10 SSD");
        produto.setMarca("Kingston");
        produto.setDescricao("Unidade SSD 480GB");
        produto.setEan("7895555555555");
        produto.setAltura(1.0);
        produto.setLargura(7.0);
        produto.setComprimento(10.0);
        produto.setPeso(0.08);

        UUID produtoId = UUID.fromString(
                mockMvc.perform(post("/api/produto/" + fornecedorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        // 3. Criar estoque
        EstoqueRequest estoque = new EstoqueRequest();
        estoque.setLocalizacao("CT10 Bloco E");
        estoque.setAndar("1º");
        estoque.setFila("F1");
        estoque.setRua("R3");
        estoque.setPrateleira("P6");

        UUID estoqueId = UUID.fromString(
                mockMvc.perform(post("/api/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estoque)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        // 4. Criar entrada
        EntradaRequest entrada = new EntradaRequest();
        entrada.setDataEntrada(LocalDate.now());
        entrada.setCustoAquisicao(BigDecimal.valueOf(499.00));
        entrada.setIdUsuario(UUID.randomUUID());

        UUID entradaId = UUID.fromString(
                mockMvc.perform(post("/api/entrada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        // 5. Criar item de estoque
        ItemEstoqueRequest item = new ItemEstoqueRequest();
        item.setIdProduto(produtoId);
        item.setIdEstoque(estoqueId);
        item.setIdEntrada(entradaId);
        item.setIdSaida(null);

        UUID itemEstoqueId = UUID.fromString(
                mockMvc.perform(post("/api/itemestoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString());

        System.out.println("📦 Item criado: " + itemEstoqueId);

        // 6. Deletar item
        mockMvc.perform(delete("/api/itemestoque/" + itemEstoqueId))
                .andExpect(status().isOk());

        System.out.println("🗑️ Item deletado com sucesso.");

        // 7. Validar que item não existe mais
        mockMvc.perform(get("/api/itemestoque/" + itemEstoqueId))
                .andExpect(status().isNotFound()); // ou .is4xxClientError() se status variar

        System.out.println("✅ Remoção confirmada via GET.");
    }
}
