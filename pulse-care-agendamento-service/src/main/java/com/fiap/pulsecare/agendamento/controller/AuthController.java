package com.fiap.pulsecare.agendamento.controller;

import com.fiap.pulsecare.agendamento.domain.dto.LoginResponseDTO;
import com.fiap.pulsecare.agendamento.domain.vo.LoginVO;
import com.fiap.pulsecare.agendamento.exception.ProblemDetailDTO;
import com.fiap.pulsecare.agendamento.sevice.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Login único da plataforma, válido em todos os módulos")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirements
	@Operation(summary = "Login", description = "Autentica um usuário e retorna um token JWT válido em todos os módulos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Credenciais inválidas ou dados de requisição inválidos", content = @Content(schema = @Schema(implementation = ProblemDetailDTO.class)))
	})
	public LoginResponseDTO login(@Valid @RequestBody LoginVO vo) {
		return authService.login(vo);
	}

}
