package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO;
import com.fiap.pulsecare.agendamento.domain.vo.AlterarSenhaVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioVO;
import com.fiap.pulsecare.agendamento.exception.ProblemDetailDTO;
import com.fiap.pulsecare.agendamento.sevice.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Usuário", description = "Cadastro e gestão de usuários (médicos, enfermeiros e pacientes)")
public class UsuarioController {

	private final UsuarioService usuarioService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Lista usuários de forma paginada", description = "Retorna uma lista paginada de usuários")
	@ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso")
	public Page<UsuarioDTO> listarPaginado(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return usuarioService.listarPaginado(pageable);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Busca usuário por id", description = "Retorna os dados de um usuário específico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		return usuarioService.buscarPorId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário (médico, enfermeiro ou paciente)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
			@ApiResponse(responseCode = "400", description = "Requisição inválida (senha fraca ou email já cadastrado)", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public UsuarioDTO cadastrar(@Valid @RequestBody UsuarioVO vo) {
		return usuarioService.cadastrar(vo);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Atualizar usuário", description = "Atualiza nome e email de um usuário existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
			@ApiResponse(responseCode = "400", description = "Requisição inválida (email já cadastrado)", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public UsuarioDTO atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateVO vo) {
		return usuarioService.atualizar(id, vo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Deletar usuário", description = "Remove um usuário existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public void deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
	}

	@PatchMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Alterar senha", description = "Altera a senha de um usuário, validando a senha atual")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Senha atual inválida, senha fraca ou confirmação não confere", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public void alterarSenha(@PathVariable Long id, @Valid @RequestBody AlterarSenhaVO vo) {
		usuarioService.alterarSenha(id, vo);
	}

}
