package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.HorarioFuncionamentoDto.CadastrarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.ListarHorarioFuncionamento;
import com.example.demo.entities.HorarioFuncionamento;
import com.example.demo.mapper.Utils.HorarioFuncionamentoMapperHelper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = HorarioFuncionamentoMapperHelper.class)
public interface HorarioFuncionamentoMapper {

    @Mapping(source = "diaFuncionamento", target = "diaFuncionamento", qualifiedByName = "converterDiaStringParaDayOfWeek")
    HorarioFuncionamento toEntity(CadastrarHorarioFuncionamento dto);

    ListarHorarioFuncionamento toDto(HorarioFuncionamento entity);

    List<ListarHorarioFuncionamento> toListDto(List<HorarioFuncionamento> list);
}
