package com.fiap.pulsecare.historico.resolver;

import com.fiap.pulsecare.historico.domain.entity.HistoricoConsulta;
import com.fiap.pulsecare.historico.repository.HistoricoConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoricoConsultaResolver {

	private final HistoricoConsultaRepository historicoConsultaRepository;

	@QueryMapping
	public List<HistoricoConsulta> historicoPorPaciente(@Argument Long pacienteId) {
		return historicoConsultaRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId);
	}

	@QueryMapping
	public List<HistoricoConsulta> consultasFuturas(@Argument Long pacienteId) {
		return historicoConsultaRepository.findByPacienteIdAndDataHoraAfterOrderByDataHoraAsc(pacienteId, LocalDateTime.now());
	}

}
