package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.MesaDto.CadastrarMesaDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.entities.Mesa;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MesaMapper {

    Mesa toEntity(CadastrarMesaDto mesaDto);

    ListarMesaDto toDto(Mesa mesa);

    List<ListarMesaDto> toDtoLIst(List<Mesa> mesas);
}
