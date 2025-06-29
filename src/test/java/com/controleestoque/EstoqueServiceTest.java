package com.controleestoque;

import com.controleestoque.entity.Estoque;
import com.controleestoque.mapper.EstoqueRequestToEntity;
import com.controleestoque.mapper.EstoqueResponseToEntity;
import com.controleestoque.model.EstoqueRequest;
import com.controleestoque.repository.EstoqueRepository;
import com.controleestoque.service.EstoqueService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private EstoqueRequestToEntity estoqueRequestToEntity;

    @Mock
    private EstoqueResponseToEntity estoqueResponseToEntity;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    public void deveCadastrarLocalizacaoComSucesso_CT14() {
        // Arrange
        EstoqueRequest request = new EstoqueRequest();
        request.setLocalizacao("Galpão A");
        request.setAndar("A1");
        request.setFila("B55");
        request.setRua("ABC123");
        request.setPrateleira("AAA10");

        Estoque estoque = new Estoque();
        UUID idEsperado = UUID.randomUUID();
        estoque.setIdEstoque(idEsperado);
        estoque.setLocalizacao("Galpão A");
        estoque.setAndar("A1");
        estoque.setFila("B55");
        estoque.setRua("ABC123");
        estoque.setPrateleira("AAA10");

        when(estoqueRequestToEntity.mapper(request)).thenReturn(estoque);
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        // Act
        UUID idRetornado = estoqueService.create(request);

        // Assert
        assertEquals(idEsperado, idRetornado);

        verify(estoqueRepository).save(argThat(e -> "Galpão A".equals(e.getLocalizacao()) &&
                "A1".equals(e.getAndar()) &&
                "B55".equals(e.getFila()) &&
                "ABC123".equals(e.getRua()) &&
                "AAA10".equals(e.getPrateleira())));
    }

    @Test
    public void deveLancarExcecaoQuandoLocalizacaoEstiverVazia_CT15() {
        // Arrange
        EstoqueRequest request = new EstoqueRequest();
        request.setLocalizacao(""); // ← campo inválido
        request.setAndar("A1");
        request.setFila("B55");
        request.setRua("ABC123");
        request.setPrateleira("AAA10");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            estoqueService.create(request);
        });

        verify(estoqueRepository, never()).save(any());
    }

}
