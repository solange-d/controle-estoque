package com.controleestoque;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.model.ProdutoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoFornecedorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID fornecedorId;

    @Test
    @Order(1)
    public void deveCadastrarFornecedor_CT02_Parte1() throws Exception {
        var request = new FornecedorRequest();
        request.setNome("Fornecedor testes");
        request.setCnpj("12345678910118");
        request.setEmail("fornecedor@fornecedor.com");
        request.setTelefone("33333333");
        request.setFabricante(false);

        MvcResult result = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        fornecedorId = UUID.fromString(body.replaceAll("\"", ""));
        System.out.println("Fornecedor criado: " + fornecedorId);
    }

    @Test
    @Order(2)
    public void deveCadastrarProdutoVinculadoAoFornecedor_CT02_Parte2() throws Exception {
        // Cria o request do produto
        ProdutoRequest request = new ProdutoRequest();
        request.setNome("Teclado RGB");
        request.setMarca("Genius");
        request.setDescricao("Teclado mecânico com iluminação");
        request.setEan("7891234567890");
        request.setAltura(4.5);
        request.setLargura(45.0);
        request.setComprimento(15.0);
        request.setPeso(0.9);

        // Imprime o JSON gerado antes de enviar
        String json = objectMapper.writeValueAsString(request);
        System.out.println("🔸 JSON enviado (produto): " + json);
        System.out.println("🔹 ID do fornecedor: " + fornecedorId);

        // Executa o teste e imprime resposta em caso de falha
        mockMvc.perform(post("/api/produto/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(result -> {
                    System.out.println("🔸 Status HTTP: " + result.getResponse().getStatus());
                    System.out.println("🔹 Corpo da resposta: " + result.getResponse().getContentAsString());
                })
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    @Order(3)
    public void deveVincularProdutoAOutroFornecedor_CT03() throws Exception {
        // 1. Criar primeiro fornecedor
        FornecedorRequest fornecedor1 = new FornecedorRequest();
        fornecedor1.setNome("Fornecedor CT03 - Primário");
        fornecedor1.setCnpj("11111111000191");
        fornecedor1.setEmail("fornecedor1@ct03.com");
        fornecedor1.setTelefone("48988888888");

        MvcResult fornecedor1Result = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor1)))
                .andExpect(status().isCreated())
                .andReturn();

        String fornecedor1Body = fornecedor1Result.getResponse().getContentAsString();
        UUID fornecedor1Id = UUID.fromString(fornecedor1Body.replaceAll("\"", ""));
        System.out.println("Fornecedor 1 criado: " + fornecedor1Id);

        // 2. Criar produto vinculado a esse fornecedor
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("Monitor CT03");
        produtoRequest.setMarca("LG");
        produtoRequest.setDescricao("Monitor Full HD 23\"");
        produtoRequest.setEan("7891111111111");
        produtoRequest.setAltura(40.0);
        produtoRequest.setLargura(55.0);
        produtoRequest.setComprimento(20.0);
        produtoRequest.setPeso(3.2);

        MvcResult produtoResult = mockMvc.perform(post("/api/produto/" + fornecedor1Id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String produtoBody = produtoResult.getResponse().getContentAsString();
        UUID produtoId = UUID.fromString(produtoBody.replaceAll("\"", ""));
        System.out.println("Produto criado: " + produtoId);

        // 3. Criar novo fornecedor para vínculo adicional
        FornecedorRequest fornecedor2 = new FornecedorRequest();
        fornecedor2.setNome("Fornecedor CT03 - Secundário");
        fornecedor2.setCnpj("22222222000192");
        fornecedor2.setEmail("fornecedor2@ct03.com");
        fornecedor2.setTelefone("48999999999");

        MvcResult fornecedor2Result = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor2)))
                .andExpect(status().isCreated())
                .andReturn();

        String fornecedor2Body = fornecedor2Result.getResponse().getContentAsString();
        UUID fornecedor2Id = UUID.fromString(fornecedor2Body.replaceAll("\"", ""));
        System.out.println("Fornecedor 2 criado: " + fornecedor2Id);

        // 4. Fazer vínculo adicional com o segundo fornecedor
        mockMvc.perform(put("/api/produto/" + produtoId + "/fornecedor/" + fornecedor2Id))
                .andExpect(status().isOk())
                .andDo(result -> {
                    System.out.println("✅ Produto vinculado ao novo fornecedor.");
                    System.out.println("Status: " + result.getResponse().getStatus());
                });
    }

}
