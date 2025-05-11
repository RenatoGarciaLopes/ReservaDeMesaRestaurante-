package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.entities.ItemDeCardapio;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItensMapper {

    @Mapping(target = "id", ignore = true)
    ItemDeCardapio toEntity(CadastrarItensDto itensDto);

    ListarItensDto toDto(ItemDeCardapio itemDeCardapio);

    List<ListarItensDto> toDtoList(List<ItemDeCardapio> itemsDeCardapio);


    
}
