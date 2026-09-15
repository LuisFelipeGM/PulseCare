package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.TipoUsuarioDTO;
import com.fiap.pulsecare.agendamento.exception.ProblemDetailDTO;
import com.fiap.pulsecare.agendamento.sevice.TipoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tipo Usuário", description = "Consulta dos papéis (roles) disponíveis na plataforma")
public class TipoUsuarioController {

    private final TipoUsuarioService tipoUsuarioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os tipos de usuário", description = "Retorna os papéis disponíveis (MEDICO, ENFERMEIRO, PACIENTE)")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public List<TipoUsuarioDTO> listarTodos() {
        return tipoUsuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca tipo de usuário por id", description = "Retorna os dados de um tipo de usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado", content = @Content(schema = @Schema(implementation = TipoUsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
    })
    public TipoUsuarioDTO buscarPorId(@PathVariable Long id) {
        return tipoUsuarioService.buscarPorId(id);
    }

}
