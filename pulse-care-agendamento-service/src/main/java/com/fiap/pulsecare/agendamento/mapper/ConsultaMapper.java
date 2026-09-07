package com.fiap.pulsecare.agendamento.mapper;

import com.fiap.pulsecare.agendamento.domain.dto.ConsultaDTO;
import com.fiap.pulsecare.agendamento.domain.entity.Consulta;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaUpdateVO;
import com.fiap.pulsecare.agendamento.domain.vo.ConsultaVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "dataCriacao", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	@Mapping(target = "paciente", ignore = true)
	@Mapping(target = "medico", ignore = true)
	Consulta toEntity(ConsultaVO vo);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "dataCriacao", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	@Mapping(target = "paciente", ignore = true)
	@Mapping(target = "medico", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromVO(ConsultaUpdateVO vo, @MappingTarget Consulta entity);

	@Mapping(source = "paciente.id", target = "pacienteId")
	@Mapping(source = "paciente.nome", target = "pacienteNome")
	@Mapping(source = "medico.id", target = "medicoId")
	@Mapping(source = "medico.nome", target = "medicoNome")
	ConsultaDTO toDTO(Consulta consulta);

}
