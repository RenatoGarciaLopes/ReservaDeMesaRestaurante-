package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.FuncionarioDto.CadastrarFuncionarioDto;
import com.example.demo.dto.FuncionarioDto.ListarFuncionarioDto;
import com.example.demo.entities.Funcionario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FuncionarioMapper {

    Funcionario toEntity(CadastrarFuncionarioDto dto);

    ListarFuncionarioDto toDto(Funcionario entity);

    List<ListarFuncionarioDto> toDtoList(List<Funcionario> entities);
}
