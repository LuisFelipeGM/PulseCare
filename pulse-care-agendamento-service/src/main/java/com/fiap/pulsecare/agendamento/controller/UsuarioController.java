package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO;
import com.fiap.pulsecare.agendamento.domain.vo.AlterarSenhaVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioVO;
import com.fiap.pulsecare.agendamento.sevice.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<UsuarioDTO> listarPaginado(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return usuarioService.listarPaginado(pageable);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		return usuarioService.buscarPorId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO cadastrar(@Valid @RequestBody UsuarioVO vo) {
		return usuarioService.cadastrar(vo);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDTO atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateVO vo) {
		return usuarioService.atualizar(id, vo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
	}

	@PatchMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.OK)
	public void alterarSenha(@PathVariable Long id, @Valid @RequestBody AlterarSenhaVO vo) {
		usuarioService.alterarSenha(id, vo);
	}

}
