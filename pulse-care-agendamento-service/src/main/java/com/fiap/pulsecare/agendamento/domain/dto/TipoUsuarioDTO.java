package com.fiap.pulsecare.agendamento.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Tipo de usuário (papel/role) da plataforma")
public class TipoUsuarioDTO {

    @Schema(description = "Identificador do tipo de usuário", example = "1")
    private Long id;

    @Schema(description = "Descrição do tipo de usuário", example = "MEDICO")
    private String descricao;
}
