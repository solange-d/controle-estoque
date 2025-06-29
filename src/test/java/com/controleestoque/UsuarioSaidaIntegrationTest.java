package com.controleestoque;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.controleestoque.model.SaidaRequest;
import com.controleestoque.model.UsuarioRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioSaidaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID usuarioCriadoId;

    @Test
    @Order(1)
    public void deveCadastrarUsuario_CT01_Parte1() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Usuario Teste");
        request.setEmail("teste@example.com");
        request.setSenha("senhaSegura123");

        MvcResult response = mockMvc.perform(
                post("/api/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        System.out.println("Corpo da resposta da criação de usuário: " + responseBody);

        usuarioCriadoId = UUID.fromString(responseBody.replaceAll("\"", ""));
        System.out.println("UUID extraído: " + usuarioCriadoId);

    }

    @Test
    @Order(2)
    public void deveCadastrarSaidaVinculadaAoUsuario_CT01_Parte2() throws Exception {
        SaidaRequest request = new SaidaRequest();
        request.setDataSaida(LocalDate.of(2025, 1, 1));
        request.setValorVenda(new BigDecimal("500.00"));
        request.setIdUsuario(usuarioCriadoId); // já persistido no passo anterior
        System.out.println("UUID extraído: " + usuarioCriadoId);

        mockMvc.perform(post("/api/saida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())))
                .andDo(result -> {
                    System.out.println("Resposta da API (status): " + result.getResponse().getStatus());
                    System.out.println("Corpo da resposta: " + result.getResponse().getContentAsString());
                });
    }

}
