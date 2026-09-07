package com.fiap.pulsecare.agendamento.sevice;

import com.fiap.pulsecare.agendamento.domain.dto.TipoUsuarioDTO;
import com.fiap.pulsecare.agendamento.domain.entity.TipoUsuario;
import com.fiap.pulsecare.agendamento.repository.TipoUsuarioRepository;
import com.fiap.pulsecare.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuarioDTO> listarTodos() {
        return tipoUsuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TipoUsuarioDTO buscarPorId(Long id) {
        return tipoUsuarioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundException("Tipo de usuário não encontrado: " + id));
    }

    private TipoUsuarioDTO toDTO(TipoUsuario tipoUsuario) {
        return new TipoUsuarioDTO(tipoUsuario.getId(), tipoUsuario.getDescricao());
    }

}
