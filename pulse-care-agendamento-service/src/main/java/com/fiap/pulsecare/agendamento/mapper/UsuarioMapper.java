package com.fiap.pulsecare.agendamento.mapper;

import com.fiap.pulsecare.agendamento.domain.dto.UsuarioDTO;
import com.fiap.pulsecare.agendamento.domain.entity.Usuario;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.UsuarioVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "senha", ignore = true)
	@Mapping(target = "dataCriacao", ignore = true)
	@Mapping(target = "tipoUsuario", ignore = true)
	Usuario toEntity(UsuarioVO vo);

	UsuarioDTO toDTO(Usuario usuario);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "senha", ignore = true)
	@Mapping(target = "dataCriacao", ignore = true)
	@Mapping(target = "tipoUsuario", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromVO(UsuarioUpdateVO vo, @MappingTarget Usuario entity);

}
