package com.fiap.pulsecare.historico.repository;

import com.fiap.pulsecare.historico.domain.entity.HistoricoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoricoConsultaRepository extends JpaRepository<HistoricoConsulta, Long> {

	Optional<HistoricoConsulta> findByConsultaId(Long consultaId);

	List<HistoricoConsulta> findByPacienteIdOrderByDataHoraDesc(Long pacienteId);

	List<HistoricoConsulta> findByPacienteIdAndDataHoraAfterOrderByDataHoraAsc(Long pacienteId, LocalDateTime referencia);

}
