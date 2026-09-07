package com.fiap.pulsecare.agendamento.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuarioDTO {

    private Long id;

    private String descricao;
}
