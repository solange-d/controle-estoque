package com.controleestoque;

import com.controleestoque.entity.Saida;
import com.controleestoque.mapper.EstoqueRequestToEntity;
import com.controleestoque.mapper.EstoqueResponseToEntity;
import com.controleestoque.mapper.SaidaRequestToEntity;
import com.controleestoque.mapper.SaidaResponseToEntity;
import com.controleestoque.model.SaidaRequest;
import com.controleestoque.repository.EstoqueRepository;
import com.controleestoque.repository.SaidaRepository;
import com.controleestoque.service.EstoqueService;
import com.controleestoque.service.SaidaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaidaServiceTest {

    @Mock
    private SaidaRepository saidaRepository;

    @Mock
    private SaidaRequestToEntity saidaRequestToEntity;

    @Mock
    private SaidaResponseToEntity saidaResponseToEntity;

    @InjectMocks
    private SaidaService saidaService;

    @Test
    public void deveCadastrarSaidaComSucesso_CT16() {
        // Arrange
        UUID idUsuario = UUID.randomUUID();

        SaidaRequest request = new SaidaRequest();
        request.setDataSaida(LocalDate.of(2025, 1, 1));
        request.setValorVenda(BigDecimal.valueOf(3000));
        request.setIdUsuario(idUsuario);

        UUID idSaidaEsperado = UUID.randomUUID();

        Saida saida = new Saida();
        saida.setIdSaida(idSaidaEsperado);
        saida.setDataSaida(request.getDataSaida());
        saida.setValorVenda(request.getValorVenda());
        // Simule o relacionamento com o usuário, se necessário

        when(saidaRequestToEntity.mapper(request)).thenReturn(saida);
        when(saidaRepository.save(any(Saida.class))).thenReturn(saida);

        // Act
        UUID idRetornado = saidaService.create(request);

        // Assert
        assertEquals(idSaidaEsperado, idRetornado);

        verify(saidaRepository).save(argThat(s -> s.getDataSaida().equals(LocalDate.of(2025, 1, 1)) &&
                s.getValorVenda().compareTo(BigDecimal.valueOf(3000)) == 0));
    }

    @Test
    public void deveLancarExcecaoQuandoValorVendaEstiverVazio_CT17() {
        // Arrange
        UUID idUsuario = UUID.randomUUID();

        SaidaRequest request = new SaidaRequest();
        request.setDataSaida(LocalDate.of(2025, 1, 1));
        request.setValorVenda(null); // ← campo inválido
        request.setIdUsuario(idUsuario);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            saidaService.create(request);
        });

        verify(saidaRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoQuandoDataSaidaEstiverComFormatoInvalido_CT18() {
        // Arrange
        UUID idUsuario = UUID.randomUUID();

        SaidaRequest request = new SaidaRequest();
        request.setDataSaida(null); // ← simula falha de deserialização
        request.setValorVenda(BigDecimal.valueOf(3000));
        request.setIdUsuario(idUsuario);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            saidaService.create(request);
        });

        verify(saidaRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioForInvalidoNaAtualizacao_CT19() {
        // Arrange
        UUID idSaida = UUID.randomUUID();
        UUID idUsuarioInvalido = UUID.fromString("00000000-0000-0000-0000-000000xxx000");

        SaidaRequest request = new SaidaRequest();
        request.setDataSaida(LocalDate.of(2025, 1, 1));
        request.setValorVenda(BigDecimal.valueOf(3000));
        request.setIdUsuario(idUsuarioInvalido);

        Saida saidaExistente = new Saida();
        saidaExistente.setIdSaida(idSaida);

        when(saidaRepository.findById(idSaida)).thenReturn(Optional.of(saidaExistente));

        // Simula que o idUsuario fornecido não corresponde a um usuário válido
        // Se o serviço buscar usuário, você deve mockar a resposta nula:
        // when(usuarioRepository.findById(idUsuarioInvalido)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            saidaService.update(idSaida, request);
        });

        verify(saidaRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoQuandoValorVendaEstiverVazioNaAtualizacao_CT20() {
        // Arrange
        UUID idSaida = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();

        SaidaRequest request = new SaidaRequest();
        request.setDataSaida(LocalDate.of(2025, 1, 1));
        request.setValorVenda(null); // ← campo vazio
        request.setIdUsuario(idUsuario);

        Saida saidaExistente = new Saida();
        saidaExistente.setIdSaida(idSaida);

        when(saidaRepository.findById(idSaida)).thenReturn(Optional.of(saidaExistente));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            saidaService.update(idSaida, request);
        });

        verify(saidaRepository, never()).save(any());
    }

}
