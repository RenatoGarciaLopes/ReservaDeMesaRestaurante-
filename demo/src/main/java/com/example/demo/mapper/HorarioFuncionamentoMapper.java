package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.HorarioFuncionamentoDto.CadastrarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.ListarHorarioFuncionamento;
import com.example.demo.entities.HorarioFuncionamento;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HorarioFuncionamentoMapper {

    HorarioFuncionamento toEntity(CadastrarHorarioFuncionamento dto);

    ListarHorarioFuncionamento toDto(HorarioFuncionamento entity);

    List<ListarHorarioFuncionamento> toListDto(List<HorarioFuncionamento> list);
}
