package com.controleestoque.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.model.UsuarioRequest;
import com.controleestoque.model.UsuarioResponse;
import com.controleestoque.service.UsuarioService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/usuario")
public class UsuarioController {
    private UsuarioService usuarioService;
    
    @GetMapping("/usuarios")
    public List<UsuarioResponse> getAll() {
        return usuarioService.getAll();
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable UUID idUsuario,
                                                         @RequestBody UsuarioRequest usuarioRequest) {
        usuarioService.update(idUsuario, usuarioRequest);
        return new ResponseEntity<UsuarioResponse>(usuarioService.getUsuarioById(idUsuario), HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> deleteUsuario(@PathVariable UUID idUsuario) {
        usuarioService.delete(idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        UUID idUsuario = usuarioService.create(usuarioRequest);
        return new ResponseEntity<>(idUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable UUID idUsuario) {
        var requestedUsuario = usuarioService.getUsuarioById(idUsuario);
        return new ResponseEntity<>(requestedUsuario, HttpStatus.OK);
    }

}