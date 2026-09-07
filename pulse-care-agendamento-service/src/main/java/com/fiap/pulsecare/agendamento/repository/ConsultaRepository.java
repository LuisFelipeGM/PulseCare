package com.fiap.pulsecare.agendamento.repository;

import com.fiap.pulsecare.agendamento.domain.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {



}
