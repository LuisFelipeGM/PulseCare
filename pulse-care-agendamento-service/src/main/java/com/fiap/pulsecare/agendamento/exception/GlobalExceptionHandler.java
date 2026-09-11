package com.fiap.pulsecare.agendamento.exception;

import com.fiap.pulsecare.core.exception.BusinessException;
import com.fiap.pulsecare.core.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ProblemDetailDTO> handleNotFound(NotFoundException ex, HttpServletRequest request) {
		log.warn("Recurso não encontrado: {}", ex.getMessage());
		return problemDetailBuilder(HttpStatus.NOT_FOUND, ex.getMessage(), request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ProblemDetailDTO> handleBusiness(BusinessException ex, HttpServletRequest request) {
		log.warn("Erro de negócio: {}", ex.getMessage());
		return problemDetailBuilder(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ProblemDetailDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

		String detail = ex.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining("; "));

		log.warn("Erro de validação: {}", detail);

		return problemDetailBuilder(HttpStatus.BAD_REQUEST, detail, request);
	}

	private ResponseEntity<ProblemDetailDTO> problemDetailBuilder(HttpStatus status, String detail, HttpServletRequest request) {

		ProblemDetailDTO body = ProblemDetailDTO.builder()
				.title(status.getReasonPhrase())
				.status(status.value())
				.detail(detail)
				.instance(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();

		return ResponseEntity.status(status).body(body);
	}

}
