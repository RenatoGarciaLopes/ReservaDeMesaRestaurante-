package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.CadastrarMesaDto;
import com.example.demo.dto.ListarMesaDto;
import com.example.demo.entities.Mesa;

@Mapper(componentModel = "spring")
public interface MesaMapper {

    Mesa toEntity(CadastrarMesaDto mesaDto);
    ListarMesaDto toDto(Mesa mesa);
    List<ListarMesaDto> toDtoLIst(List<Mesa> mesas);
}
