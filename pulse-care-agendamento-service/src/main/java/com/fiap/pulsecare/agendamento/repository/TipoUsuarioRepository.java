package com.fiap.pulsecare.agendamento.repository;

import com.fiap.pulsecare.agendamento.domain.entity.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {



}
