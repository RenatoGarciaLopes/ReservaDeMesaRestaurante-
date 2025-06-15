package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.CategoriaDto.CadastrarCategoriaDto;
import com.example.demo.dto.CategoriaDto.ListarCategoriaDto;
import com.example.demo.entities.Categoria;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    Categoria toEntity(CadastrarCategoriaDto dto);

    ListarCategoriaDto toDto(Categoria entity);

    List<ListarCategoriaDto> toListDto(List<Categoria> entities);
}
