package com.fiap.pulsecare.agendamento.repository;

import com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO;
import com.fiap.pulsecare.agendamento.domain.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("SELECT NEW com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO(" +
			"u.id, u.nome, u.email, u.tipoUsuario.id, u.tipoUsuario.descricao, u.dataCriacao) " +
			"FROM Usuario u " +
			"ORDER BY u.id ASC")
	Page<UsuarioDTO> findAllPaginado(Pageable pageable);

	@Query("SELECT NEW com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO(" +
			"u.id, u.nome, u.email, u.tipoUsuario.id, u.tipoUsuario.descricao, u.dataCriacao) " +
			"FROM Usuario u " +
			"WHERE u.id = :id")
	Optional<UsuarioDTO> findByIdUsuario(@Param("id") Long id);

	Optional<Usuario> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, Long id);

}
