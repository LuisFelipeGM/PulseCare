package com.fiap.pulsecare.agendamento.sevice;

import com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO;
import com.fiap.pulsecare.agendamento.domain.entity.Consulta;
import com.fiap.pulsecare.agendamento.domain.entity.Usuario;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaVO;
import com.fiap.pulsecare.agendamento.mapper.ConsultaMapper;
import com.fiap.pulsecare.agendamento.repository.ConsultaRepository;
import com.fiap.pulsecare.agendamento.repository.UsuarioRepository;
import com.fiap.pulsecare.core.exception.BusinessException;
import com.fiap.pulsecare.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaService {

	private final ConsultaRepository consultaRepository;
	private final UsuarioRepository usuarioRepository;
	private final ConsultaMapper consultaMapper;

	@Transactional
	public ConsultaDTO cadastrar(ConsultaVO vo) {

		log.info("Iniciando cadastro de consulta para paciente {} e médico {}", vo.getPacienteId(), vo.getMedicoId());

		Usuario paciente = usuarioRepository.findById(vo.getPacienteId())
				.orElseThrow(() -> new NotFoundException("Paciente não encontrado: " + vo.getPacienteId()));

		Usuario medico = usuarioRepository.findById(vo.getMedicoId())
				.orElseThrow(() -> new NotFoundException("Médico não encontrado: " + vo.getMedicoId()));

		Consulta consulta = consultaMapper.toEntity(vo);
		consulta.setPaciente(paciente);
		consulta.setMedico(medico);

		consulta = consultaRepository.save(consulta);

		log.info("Consulta cadastrada com sucesso, id: {}", consulta.getId());

		return consultaMapper.toDTO(consulta);
	}

	@Transactional
	public ConsultaDTO atualizar(Long id, ConsultaUpdateVO vo) {

		log.info("Iniciando a atualização da consulta com ID: {}", id);

		Consulta consulta = consultaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Consulta não encontrada: " + id));

		consultaMapper.updateFromVO(vo, consulta);

		consulta = consultaRepository.save(consulta);

		log.info("Consulta com ID: {} atualizada com sucesso", id);

		return consultaMapper.toDTO(consulta);
	}

	public ConsultaDTO buscarPorId(Long id) {
		return consultaRepository.findByIdConsulta(id)
				.orElseThrow(() -> new NotFoundException("Consulta não encontrada: " + id));
	}

	public Page<ConsultaDTO> listar(Long pacienteId, Long medicoId, Pageable pageable) {

		boolean temPaciente = pacienteId != null;
		boolean temMedico = medicoId != null;

		if (temPaciente == temMedico) {
			throw new BusinessException("Informe exatamente um filtro: pacienteId ou medicoId");
		}

		return temPaciente
				? consultaRepository.findAllByPacienteId(pacienteId, pageable)
				: consultaRepository.findAllByMedicoId(medicoId, pageable);
	}

}
