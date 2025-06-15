package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.entities.ItemDeCardapio;
import com.example.demo.mapper.Utils.ItemHelperMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ItemHelperMapper.class)
public interface ItensMapper {

    @Mapping(source = "categoriaId", target = "categoria", qualifiedByName = "buscaCategoriaPorId")
    ItemDeCardapio toEntity(CadastrarItensDto itensDto);

    @Mapping(target = "categoria", source = "categoria.nome")
    ListarItensDto toDto(ItemDeCardapio itemDeCardapio);

    List<ListarItensDto> toDtoList(List<ItemDeCardapio> itemsDeCardapio);
}
