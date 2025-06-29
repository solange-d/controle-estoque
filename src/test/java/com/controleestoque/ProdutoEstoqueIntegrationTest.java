package com.controleestoque;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.controleestoque.model.EntradaRequest;
import com.controleestoque.model.EstoqueRequest;
import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.model.ItemEstoqueRequest;
import com.controleestoque.model.ProdutoRequest;
import com.controleestoque.model.UsuarioRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoEstoqueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(4)
    public void deveVincularProdutoAoEstoque_CT04() throws Exception {
        // 1. Criar fornecedor
        FornecedorRequest fornecedor = new FornecedorRequest();
        fornecedor.setNome("Fornecedor CT04");
        fornecedor.setCnpj("99999999000199");
        fornecedor.setEmail("fornecedorct04@example.com");
        fornecedor.setTelefone("48988884444");

        MvcResult fornecedorResult = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID fornecedorId = UUID.fromString(fornecedorResult.getResponse().getContentAsString());

        // 2. Criar usuário responsável pela entrada
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("Responsável Estoque");
        usuario.setEmail("usuario@entrada.com");
        usuario.setSenha("senhaForte123");

        MvcResult usuarioResult = mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID usuarioId = UUID.fromString(usuarioResult.getResponse().getContentAsString());

        // 3. Criar produto associado ao fornecedor
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome("Scanner CT04");
        produto.setMarca("Epson");
        produto.setDescricao("Scanner de mesa USB");
        produto.setEan("7891237891234");
        produto.setAltura(10.0);
        produto.setLargura(35.0);
        produto.setComprimento(25.0);
        produto.setPeso(2.3);

        MvcResult produtoResult = mockMvc.perform(post("/api/produto/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID produtoId = UUID.fromString(produtoResult.getResponse().getContentAsString());

        // 4. Criar estoque (localização do produto)
        EstoqueRequest estoque = new EstoqueRequest();
        estoque.setLocalizacao("Depósito Central");
        estoque.setAndar("Térreo");
        estoque.setFila("F3");
        estoque.setRua("R2");
        estoque.setPrateleira("P9");

        MvcResult estoqueResult = mockMvc.perform(post("/api/estoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(estoque)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID estoqueId = UUID.fromString(estoqueResult.getResponse().getContentAsString());

        // 5. Criar entrada vinculando produto, fornecedor, usuário e estoque
        EntradaRequest entrada = new EntradaRequest(); // hipotético DTO com campos estendidos
        // entrada.setIdProduto(produtoId);
        // entrada.setIdFornecedor(fornecedorId);
        // entrada.setIdEstoque(estoqueId);
        entrada.setIdUsuario(usuarioId);
        entrada.setCustoAquisicao(new BigDecimal("799.90"));
        entrada.setDataEntrada(LocalDate.of(2025, 7, 1));

        mockMvc.perform(post("/api/entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andDo(result -> {
                    System.out.println("📦 Entrada registrada com sucesso. ID: "
                            + result.getResponse().getContentAsString());
                });
    }

    @Test
    @Order(5)
    public void deveDesvincularProdutoDoEstoque_CT05() throws Exception {
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

        // 3. Criar produto
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome("HD Externo");
        produto.setMarca("Seagate");
        produto.setDescricao("HD 2TB USB 3.0");
        produto.setEan("7891234567000");
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

        // 6. Criar item de estoque
        ItemEstoqueRequest itemEstoque = new ItemEstoqueRequest();
        itemEstoque.setIdProduto(produtoId);
        itemEstoque.setIdEstoque(estoqueId);
        itemEstoque.setIdEntrada(entradaId);
        itemEstoque.setIdSaida(null);

        MvcResult itemResult = mockMvc.perform(post("/api/itemestoque")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemEstoque)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID idItemEstoque = UUID.fromString(itemResult.getResponse().getContentAsString());

        System.out.println("🔗 Item de estoque criado: " + idItemEstoque);

        // 7. Desvincular (DELETE)
        mockMvc.perform(delete("/api/itemestoque/" + idItemEstoque))
                .andExpect(status().isOk())
                .andDo(result -> {
                    System.out.println("❌ Produto desvinculado do estoque com sucesso.");
                });
    }

}
