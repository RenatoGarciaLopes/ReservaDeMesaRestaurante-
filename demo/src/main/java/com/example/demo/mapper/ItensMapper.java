package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.CadastrarItensDto;
import com.example.demo.dto.ListarItensDto;
import com.example.demo.entities.ItemDeCardapio;

@Mapper(componentModel = "spring")
public interface ItensMapper {

    @Mapping(target = "id", ignore = true)
    ItemDeCardapio toEntity(CadastrarItensDto itensDto);
    ListarItensDto tDto(ItemDeCardapio itemDeCardapio);
    List<ListarItensDto> toDtoList(List<ItemDeCardapio> itemsDeCardapio);


    
}
