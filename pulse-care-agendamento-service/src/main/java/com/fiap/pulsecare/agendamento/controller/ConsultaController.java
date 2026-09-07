package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaVO;
import com.fiap.pulsecare.agendamento.sevice.ConsultaService;
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
public class ConsultaController {

	private final ConsultaService consultaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ConsultaDTO cadastrar(@Valid @RequestBody ConsultaVO vo) {
		return consultaService.cadastrar(vo);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ConsultaDTO atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaUpdateVO vo) {
		return consultaService.atualizar(id, vo);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ConsultaDTO buscarPorId(@PathVariable Long id) {
		return consultaService.buscarPorId(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<ConsultaDTO> listar(
			@RequestParam(required = false) Long pacienteId,
			@RequestParam(required = false) Long medicoId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return consultaService.listar(pacienteId, medicoId, pageable);
	}

}
