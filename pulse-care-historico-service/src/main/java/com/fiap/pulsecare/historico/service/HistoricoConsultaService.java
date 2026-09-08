package com.fiap.pulsecare.historico.service;

import com.fiap.pulsecare.core.messaging.ConsultaEventoDTO;
import com.fiap.pulsecare.historico.domain.entity.HistoricoConsulta;
import com.fiap.pulsecare.historico.repository.HistoricoConsultaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoConsultaService {

	private final HistoricoConsultaRepository historicoConsultaRepository;

	@Transactional
	public void registrarOuAtualizar(ConsultaEventoDTO evento) {

		HistoricoConsulta historico = historicoConsultaRepository.findByConsultaId(evento.consultaId())
				.orElseGet(HistoricoConsulta::new);

		historico.setConsultaId(evento.consultaId());
		historico.setPacienteId(evento.pacienteId());
		historico.setPacienteNome(evento.pacienteNome());
		historico.setMedicoId(evento.medicoId());
		historico.setMedicoNome(evento.medicoNome());
		historico.setDataHora(evento.dataHora());
		historico.setStatus(evento.status());
		historico.setDataAtualizacao(LocalDateTime.now());

		historicoConsultaRepository.save(historico);

		log.info("Projeção da consulta {} salva no histórico (evento {})", evento.consultaId(), evento.tipoEvento());
	}

}
