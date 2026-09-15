package com.fiap.pulsecare.agendamento.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.net.URI;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Corpo de erro padrão da API, no formato RFC 7807 (Problem Details)")
public class ProblemDetailDTO {

	@Builder.Default
	@Schema(description = "URI que identifica o tipo do problema", example = "about:blank")
	private URI type = URI.create("about:blank");

	@Schema(description = "Título curto do erro (reason phrase do status HTTP)", example = "Bad Request")
	private String title;

	@Schema(description = "Código de status HTTP", example = "400")
	private int status;

	@Schema(description = "Mensagem detalhada do erro", example = "Email já cadastrado: joao@x.com")
	private String detail;

	@Schema(description = "Caminho da requisição que gerou o erro", example = "/usuarios")
	private String instance;

	@Schema(description = "Momento em que o erro ocorreu", example = "2026-09-14T10:30:00")
	private LocalDateTime timestamp;

}
