package com.fiap.pulsecare.agendamento.repository;

import com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO;
import com.fiap.pulsecare.agendamento.domain.entity.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	@Query("SELECT NEW com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO(" +
			"c.id, c.paciente.id, c.paciente.nome, c.medico.id, c.medico.nome, " +
			"c.dataHora, c.status, c.dataCriacao, c.dataAtualizacao) " +
			"FROM Consulta c " +
			"WHERE c.id = :id")
	Optional<ConsultaDTO> findByIdConsulta(@Param("id") Long id);

	@Query("SELECT NEW com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO(" +
			"c.id, c.paciente.id, c.paciente.nome, c.medico.id, c.medico.nome, " +
			"c.dataHora, c.status, c.dataCriacao, c.dataAtualizacao) " +
			"FROM Consulta c " +
			"WHERE c.paciente.id = :pacienteId " +
			"ORDER BY c.dataHora DESC")
	Page<ConsultaDTO> findAllByPacienteId(@Param("pacienteId") Long pacienteId, Pageable pageable);

	@Query("SELECT NEW com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO(" +
			"c.id, c.paciente.id, c.paciente.nome, c.medico.id, c.medico.nome, " +
			"c.dataHora, c.status, c.dataCriacao, c.dataAtualizacao) " +
			"FROM Consulta c " +
			"WHERE c.medico.id = :medicoId " +
			"ORDER BY c.dataHora DESC")
	Page<ConsultaDTO> findAllByMedicoId(@Param("medicoId") Long medicoId, Pageable pageable);

}
