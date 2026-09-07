package com.fiap.pulsecare.agendamento.sevice;

import com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO;
import com.fiap.pulsecare.agendamento.domain.entity.TipoUsuario;
import com.fiap.pulsecare.agendamento.domain.entity.Usuario;
import com.fiap.pulsecare.agendamento.domain.vo.AlterarSenhaVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioVO;
import com.fiap.pulsecare.agendamento.mapper.UsuarioMapper;
import com.fiap.pulsecare.agendamento.repository.TipoUsuarioRepository;
import com.fiap.pulsecare.agendamento.repository.UsuarioRepository;
import com.fiap.pulsecare.agendamento.validation.UsuarioValidator;
import com.fiap.pulsecare.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final TipoUsuarioRepository tipoUsuarioRepository;
	private final UsuarioValidator usuarioValidator;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passwordEncoder;

	public Page<UsuarioDTO> listarPaginado(Pageable pageable) {
		return usuarioRepository.findAllPaginado(pageable);
	}

	public UsuarioDTO buscarPorId(Long id) {
		return usuarioRepository.findByIdUsuario(id)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));
	}

	@Transactional
	public UsuarioDTO cadastrar(UsuarioVO vo) {

		log.info("Iniciando cadastro de usuário com email: {}", vo.getEmail());

		usuarioValidator.validarCriacao(vo);

		TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(vo.getTipoUsuarioId())
				.orElseThrow(() -> new NotFoundException("Tipo de usuário não encontrado: " + vo.getTipoUsuarioId()));

		Usuario usuario = usuarioMapper.toEntity(vo);
		usuario.setSenha(passwordEncoder.encode(vo.getSenha()));
		usuario.setTipoUsuario(tipoUsuario);

		usuario = usuarioRepository.save(usuario);

		log.info("Usuário cadastrado com sucesso, id: {}", usuario.getId());

		return usuarioMapper.toDTO(usuario);
	}

	@Transactional
	public UsuarioDTO atualizar(Long id, UsuarioUpdateVO vo) {

		log.info("Iniciando a atualização do usuário com ID: {}", id);

		usuarioValidator.validarAtualizacao(vo, id);

		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

		usuarioMapper.updateFromVO(vo, usuario);

		usuarioRepository.save(usuario);

		log.info("Usuário com ID: {} atualizado com sucesso", id);

		return buscarPorId(id);
	}

	@Transactional
	public void deletar(Long id) {

		log.info("Iniciando a exclusão do usuário com ID: {}", id);

		usuarioValidator.validarExclusao(id);

		usuarioRepository.deleteById(id);

		log.info("Usuário com ID: {} excluído com sucesso", id);
	}

	@Transactional
	public void alterarSenha(Long id, AlterarSenhaVO vo) {

		log.info("Iniciando a troca de senha do usuário com ID: {}", id);

		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

		usuarioValidator.validarTrocaSenha(usuario, vo);

		usuario.setSenha(passwordEncoder.encode(vo.getNovaSenha()));

		usuarioRepository.save(usuario);

		log.info("Senha do usuário com ID: {} alterada com sucesso", id);
	}

}
