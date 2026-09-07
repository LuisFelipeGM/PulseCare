package com.fiap.pulsecare.agendamento.validation;

import com.fiap.pulsecare.agendamento.domain.entity.Usuario;
import com.fiap.pulsecare.agendamento.domain.vo.AlterarSenhaVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioVO;
import com.fiap.pulsecare.agendamento.repository.UsuarioRepository;
import com.fiap.pulsecare.core.exception.BusinessException;
import com.fiap.pulsecare.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioValidator {

	private static final Pattern SENHA_FORTE_PATTERN =
			Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$");

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public void validarCriacao(UsuarioVO vo) {

		log.info("Validando cadastro de usuário com email: {}", vo.getEmail());

		if (usuarioRepository.existsByEmail(vo.getEmail())) {
			throw new BusinessException("Email já cadastrado: " + vo.getEmail());
		}

		validarSenhaForte(vo.getSenha());
	}

	public void validarAtualizacao(UsuarioUpdateVO vo, Long id) {

		log.info("Validando atualização do usuário com ID: {}", id);

		if (usuarioRepository.existsByEmailAndIdNot(vo.getEmail(), id)) {
			throw new BusinessException("Email já cadastrado: " + vo.getEmail());
		}
	}

	public void validarExclusao(Long id) {

		log.info("Validando exclusão do usuário com ID: {}", id);

		if (!usuarioRepository.existsById(id)) {
			throw new NotFoundException("Usuário não encontrado: " + id);
		}
	}

	public void validarTrocaSenha(Usuario usuario, AlterarSenhaVO vo) {

		log.info("Validando troca de senha do usuário com ID: {}", usuario.getId());

		if (!passwordEncoder.matches(vo.getSenhaAtual(), usuario.getSenha())) {
			throw new BusinessException("Senha atual inválida");
		}

		validarSenhaForte(vo.getNovaSenha());

		if (passwordEncoder.matches(vo.getNovaSenha(), usuario.getSenha())) {
			throw new BusinessException("A nova senha deve ser diferente da atual");
		}

		if (!Objects.equals(vo.getNovaSenha(), vo.getConfirmacaoSenha())) {
			throw new BusinessException("Confirmação de senha não confere");
		}
	}

	private void validarSenhaForte(String senha) {

		if (senha == null || !SENHA_FORTE_PATTERN.matcher(senha).matches()) {
			log.warn("Tentativa de cadastro com senha inválida.");
			throw new BusinessException(
					"Senha deve ter no mínimo 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial");
		}
	}

}
