package com.controleestoque;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.controleestoque.model.EnderecoRequest;
import com.controleestoque.model.FornecedorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FornecedorEnderecoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(6)
    public void deveCadastrarEnderecoParaFornecedor_CT06() throws Exception {
        // 1. Criar fornecedor
        FornecedorRequest fornecedor = new FornecedorRequest();
        fornecedor.setNome("Fornecedor CT06");
        fornecedor.setCnpj("12345678900066");
        fornecedor.setEmail("ct06@fornecedor.com");
        fornecedor.setTelefone("48999996666");

        MvcResult fornecedorResult = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID fornecedorId = UUID.fromString(fornecedorResult.getResponse().getContentAsString());

        // 2. Cadastrar endereço para esse fornecedor
        EnderecoRequest endereco = new EnderecoRequest();
        endereco.setCep(88900000);
        endereco.setPais("Brasil");
        endereco.setEstado("Santa Catarina");
        endereco.setMunicipio("Rio do Sul");
        endereco.setBairro("Centro");
        endereco.setLogradouro("Rua das Palmeiras");
        endereco.setNumero("123");
        endereco.setReferencia("Ao lado do Mercado Central");

        MvcResult enderecoResult = mockMvc.perform(post("/api/endereco/fornecedor/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID enderecoId = UUID.fromString(enderecoResult.getResponse().getContentAsString());
        System.out.println("🏠 Endereço vinculado ao fornecedor. ID: " + enderecoId);

        // 3. Buscar todos os endereços do fornecedor para verificar o vínculo
        MvcResult getResponse = mockMvc.perform(get("/api/endereco/fornecedor/" + fornecedorId))
                .andExpect(status().isOk())
                .andReturn();

        String body = getResponse.getResponse().getContentAsString();
        System.out.println("📋 Lista de endereços vinculados: " + body);

        // 💡 Aqui você pode adicionar asserts para verificar o conteúdo
        assertThat(body).contains("Rua das Palmeiras");
        assertThat(body).contains("Centro");
        assertThat(body).contains("88900000");
    }

    @Test
    @Order(7)
    public void deveListarEnderecosDoFornecedor_CT07() throws Exception {
        // 1. Criar fornecedor
        FornecedorRequest fornecedor = new FornecedorRequest();
        fornecedor.setNome("Fornecedor CT07");
        fornecedor.setCnpj("12345678900077");
        fornecedor.setEmail("ct07@fornecedor.com");
        fornecedor.setTelefone("48999997777");

        MvcResult fornecedorResult = mockMvc.perform(post("/api/fornecedor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andReturn();

        UUID fornecedorId = UUID.fromString(fornecedorResult.getResponse().getContentAsString());

        // 2. Cadastrar dois endereços para esse fornecedor
        EnderecoRequest endereco1 = new EnderecoRequest();
        endereco1.setCep(88900001);
        endereco1.setPais("Brasil");
        endereco1.setEstado("SC");
        endereco1.setMunicipio("Rio do Sul");
        endereco1.setBairro("Centro");
        endereco1.setLogradouro("Rua A");
        endereco1.setNumero("100");
        endereco1.setReferencia("Próximo ao banco");

        EnderecoRequest endereco2 = new EnderecoRequest();
        endereco2.setCep(88900002);
        endereco2.setPais("Brasil");
        endereco2.setEstado("SC");
        endereco2.setMunicipio("Rio do Sul");
        endereco2.setBairro("Bela Aliança");
        endereco2.setLogradouro("Rua B");
        endereco2.setNumero("200");
        endereco2.setReferencia("Ao lado do mercado");

        mockMvc.perform(post("/api/endereco/fornecedor/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/endereco/fornecedor/" + fornecedorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco2)))
                .andExpect(status().isCreated());

        // 3. Recuperar lista de endereços
        MvcResult getResult = mockMvc.perform(get("/api/endereco/fornecedor/" + fornecedorId))
                .andExpect(status().isOk())
                .andReturn();

        String json = getResult.getResponse().getContentAsString();
        System.out.println("📋 Lista de endereços retornada: " + json);

        // 4. Validar conteúdo
        assertThat(json).contains("Rua A");
        assertThat(json).contains("Rua B");
        assertThat(json).contains("Centro");
        assertThat(json).contains("Bela Aliança");
    }

}
