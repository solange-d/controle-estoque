package com.controleestoque;

import com.controleestoque.entity.Usuario;
import com.controleestoque.mapper.UsuarioRequestToEntity;
import com.controleestoque.model.UsuarioRequest;
import com.controleestoque.model.UsuarioResponse;
import com.controleestoque.repository.UsuarioRepository;
import com.controleestoque.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioRequestToEntity usuarioRequestToEntity;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void deveCadastrarUsuarioValido_CT01() {
        // Arrange
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        UsuarioRequestToEntity mapper = mock(UsuarioRequestToEntity.class);

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        // configuração dos dados de teste
        usuarioRequest.setNome("Ana");
        usuarioRequest.setCpf("12345678900");
        usuarioRequest.setEmail("ana@email.com");
        usuarioRequest.setSenha("12345678");
        usuarioRequest.setAdministrador(true);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(UUID.randomUUID());

        // Comportamento dos mocks
        when(mapper.mapper(usuarioRequest)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioService service = new UsuarioService(mapper, null, usuarioRepository);

        // Act
        UUID resultado = service.create(usuarioRequest);

        // Exibir o UUID no terminal
        System.out.println("UUID retornado: " + resultado);

        // Assert
        assertEquals(usuario.getIdUsuario(), resultado);
        verify(usuarioRepository).save(usuario); // opcional, para verificar que o save foi chamado

    }

    @Test
    public void deveLancarExcecaoAoCadastrarUsuarioComNomeVazio_CT02() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest();
        request.setNome(""); // nome vazio
        request.setCpf("12345678900");
        request.setEmail("ana@email.com");
        request.setSenha("12345678");
        request.setAdministrador(true);

        Usuario usuario = new Usuario(); // ou pode simular o comportamento do mapper
        when(usuarioRequestToEntity.mapper(request)).thenReturn(usuario);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.create(request);
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoAoCadastrarUsuarioComEmailInvalido_CT03() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("ana");
        request.setCpf("12345678900");
        request.setEmail("ana"); // e-mail inválido
        request.setSenha("12345678");
        request.setAdministrador(true);

        Usuario usuario = new Usuario(); // poderia conter validação também
        when(usuarioRequestToEntity.mapper(request)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario); // opcional, se a validação acontecer antes

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.create(request);
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoAoCadastrarUsuarioComSenhaCurta_CT04() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("ana");
        request.setCpf("12345678900");
        request.setEmail("ana@email.com");
        request.setSenha("123"); // senha muito curta
        request.setAdministrador(true);

        Usuario usuario = new Usuario();
        when(usuarioRequestToEntity.mapper(request)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.create(request);
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoAoCadastrarUsuarioComCpfDuplicado_CT05() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("ana");
        request.setCpf("12345678900");
        request.setEmail("ana@email.com");
        request.setSenha("12345678");
        request.setAdministrador(true);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setCpf("12345678900");

        // Simula que já existe um usuário com o mesmo CPF no banco
        when(usuarioRepository.findByCpf("12345678900")).thenReturn(Optional.of(usuarioExistente));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.create(request);
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    public void deveLancarExcecaoAoAtualizarUsuarioComAdministradorInvalido_CT06() {
        // Arrange
        UUID idUsuario = UUID.randomUUID();

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("ana");
        request.setCpf("12345678900");
        request.setEmail("ana@email.com");
        request.setSenha("12345678");
        // Simulando que o campo administrador não foi preenchido corretamente
        // Como não temos como representar isso como "", usamos false e assumimos que o
        // campo é inválido nesse contexto
        request.setAdministrador(false);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(idUsuario);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.update(idUsuario, request);
        });

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    public void deveAtualizarUsuarioComSucesso_CT07() {
        // Arrange
        UUID id = UUID.randomUUID();

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("ana");
        request.setCpf("12345678900");
        request.setEmail("ana@email.com");
        request.setSenha("12345678");
        request.setAdministrador(false);

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(id);
        usuarioExistente.setNome("antigo");
        usuarioExistente.setCpf("00000000000");
        usuarioExistente.setEmail("velho@email.com");
        usuarioExistente.setSenha("senhaantiga");
        usuarioExistente.setAdministrador(true);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioResponse response = usuarioService.update(id, request);

        // Assert
        assertEquals("ana", response.getNome());
        assertEquals("12345678900", response.getCpf());
        assertEquals("ana@email.com", response.getEmail());
        assertEquals(false, response.isAdministrador());

        // Confirma que o método save foi chamado
        verify(usuarioRepository).save(argThat(usuario -> usuario.getNome().equals("ana") &&
                usuario.getCpf().equals("12345678900") &&
                usuario.getEmail().equals("ana@email.com") &&
                usuario.getSenha().equals("12345678") &&
                !usuario.isAdministrador()));
    }

    @Test
    public void deveExcluirUsuarioValido_CT08() {
        // Arrange
        UUID idUsuario = UUID.randomUUID();

        // Simula que o usuário existe no repositório
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));

        // Act
        usuarioService.delete(idUsuario);

        // Assert
        verify(usuarioRepository).deleteById(idUsuario); // Confirma que o método foi chamado com o ID correto
    }

}