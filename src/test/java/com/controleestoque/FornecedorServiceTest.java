package com.controleestoque;

import com.controleestoque.entity.Fornecedor;
import com.controleestoque.mapper.FornecedorRequestToEntity;
import com.controleestoque.model.FornecedorRequest;
import com.controleestoque.repository.FornecedorRepository;
import com.controleestoque.service.FornecedorService;

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
public class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private FornecedorRequestToEntity fornecedorRequestToEntity;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    public void deveCadastrarFornecedorComSucesso_CT09() {
        // Arrange
        FornecedorRequest request = new FornecedorRequest();
        request.setNome("Fornecedor");
        request.setCnpj("12345678910111");
        request.setEmail("fornecedor@fornecedor.com");
        request.setTelefone("33333333");
        request.setFabricante(false);

        UUID idEsperado = UUID.randomUUID();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(idEsperado);
        fornecedor.setNome("Fornecedor");
        fornecedor.setCnpj("12345678910111");
        fornecedor.setEmail("fornecedor@fornecedor.com");
        fornecedor.setTelefone("33333333");
        fornecedor.setFabricante(false);

        when(fornecedorRequestToEntity.mapper(request)).thenReturn(fornecedor);
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedor);

        // Act
        UUID idRetornado = fornecedorService.create(request);

        // Assert
        assertEquals(idEsperado, idRetornado);

        verify(fornecedorRepository).save(argThat(f -> f.getNome().equals("Fornecedor") &&
                f.getCnpj().equals("12345678910111") &&
                f.getEmail().equals("fornecedor@fornecedor.com") &&
                f.getTelefone().equals("33333333") &&
                !f.isFabricante()));
    }

    @Test
    public void deveLancarExcecaoAoCadastrarFornecedorComNomeVazio_CT10() {
        // Arrange
        FornecedorRequest request = new FornecedorRequest();
        request.setNome(""); // nome inválido
        request.setCnpj("12345678910111");
        request.setEmail("fornecedor@fornecedor.com");
        request.setTelefone("33333333");
        request.setFabricante(false);

        Fornecedor fornecedor = new Fornecedor();
        when(fornecedorRequestToEntity.mapper(request)).thenReturn(fornecedor);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            fornecedorService.create(request);
        });

        verify(fornecedorRepository, never()).save(any());
    }

}
