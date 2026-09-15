package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaVO;
import com.fiap.pulsecare.agendamento.exception.ProblemDetailDTO;
import com.fiap.pulsecare.agendamento.sevice.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
@Tag(name = "Consulta", description = "Cadastro, edição, busca e listagem de consultas médicas")
public class ConsultaController {

	private final ConsultaService consultaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Cadastrar consulta", description = "Agenda uma nova consulta. Restrito ao papel MEDICO")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Consulta cadastrada com sucesso", content = @Content(schema = @Schema(implementation = ConsultaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Data no passado ou requisição inválida", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "403", description = "Usuário sem permissão (requer papel MEDICO)", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "404", description = "Paciente ou médico não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public ConsultaDTO cadastrar(@Valid @RequestBody ConsultaVO vo) {
		return consultaService.cadastrar(vo);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Atualizar consulta", description = "Atualiza data/hora e status de uma consulta. Restrito aos papéis MEDICO e ENFERMEIRO")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso", content = @Content(schema = @Schema(implementation = ConsultaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Data no passado ou requisição inválida", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "403", description = "Usuário sem permissão (requer papel MEDICO ou ENFERMEIRO)", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "404", description = "Consulta não encontrada", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public ConsultaDTO atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaUpdateVO vo) {
		return consultaService.atualizar(id, vo);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Busca consulta por id", description = "Retorna os dados de uma consulta específica")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Consulta encontrada", content = @Content(schema = @Schema(implementation = ConsultaDTO.class))),
			@ApiResponse(responseCode = "404", description = "Consulta não encontrada", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public ConsultaDTO buscarPorId(@PathVariable Long id) {
		return consultaService.buscarPorId(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Lista consultas por paciente ou médico", description = "Retorna as consultas paginadas de um paciente ou de um médico — informe exatamente um dos dois filtros")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Nenhum filtro ou os dois filtros informados ao mesmo tempo", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public Page<ConsultaDTO> listar(
			@Parameter(description = "ID do paciente (não pode ser usado junto com medicoId)") @RequestParam(required = false) Long pacienteId,
			@Parameter(description = "ID do médico (não pode ser usado junto com pacienteId)") @RequestParam(required = false) Long medicoId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return consultaService.listar(pacienteId, medicoId, pageable);
	}

}
