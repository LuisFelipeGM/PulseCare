package com.fiap.pulsecare.agendamento.exception;

import lombok.Builder;
import lombok.Getter;

import java.net.URI;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProblemDetailDTO {

	@Builder.Default
	private URI type = URI.create("about:blank");

	private String title;
	private int status;
	private String detail;
	private String instance;
	private LocalDateTime timestamp;

}
