package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.TipoUsuarioDTO;
import com.fiap.pulsecare.agendamento.sevice.TipoUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tipos-usuario")
@RequiredArgsConstructor
public class TipoUsuarioController {

    private final TipoUsuarioService tipoUsuarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TipoUsuarioDTO> listarTodos() {
        return tipoUsuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoUsuarioDTO buscarPorId(@PathVariable Long id) {
        return tipoUsuarioService.buscarPorId(id);
    }

}
