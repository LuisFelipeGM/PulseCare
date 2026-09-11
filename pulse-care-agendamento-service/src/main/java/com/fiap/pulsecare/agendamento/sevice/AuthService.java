package com.fiap.pulsecare.agendamento.sevice;

import com.fiap.pulsecare.agendamento.domain.dto.LoginResponseDTO;
import com.fiap.pulsecare.agendamento.domain.entity.Usuario;
import com.fiap.pulsecare.agendamento.domain.vo.LoginVO;
import com.fiap.pulsecare.agendamento.repository.UsuarioRepository;
import com.fiap.pulsecare.core.exception.BusinessException;
import com.fiap.pulsecare.core.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Transactional(readOnly = true)
	public LoginResponseDTO login(LoginVO vo) {

		log.info("Tentativa de login com email: {}", vo.getEmail());

		Usuario usuario = usuarioRepository.findByEmail(vo.getEmail())
				.orElseThrow(() -> new BusinessException("Credenciais inválidas"));

		if (!passwordEncoder.matches(vo.getSenha(), usuario.getSenha())) {
			throw new BusinessException("Credenciais inválidas");
		}

		String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getTipoUsuario().getDescricao());

		log.info("Login bem-sucedido para o usuário id: {}", usuario.getId());

		return LoginResponseDTO.builder()
				.token(token)
				.build();
	}

}
