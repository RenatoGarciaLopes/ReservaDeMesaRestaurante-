package com.example.demo.mapper;

import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.entities.ItemDeCardapio;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-21T19:25:29-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ItensMapperImpl implements ItensMapper {

    @Override
    public ItemDeCardapio toEntity(CadastrarItensDto itensDto) {
        if ( itensDto == null ) {
            return null;
        }

        ItemDeCardapio itemDeCardapio = new ItemDeCardapio();

        itemDeCardapio.setDescricao( itensDto.getDescricao() );
        itemDeCardapio.setNome( itensDto.getNome() );
        itemDeCardapio.setPreco( itensDto.getPreco() );

        return itemDeCardapio;
    }

    @Override
    public ListarItensDto toDto(ItemDeCardapio itemDeCardapio) {
        if ( itemDeCardapio == null ) {
            return null;
        }

        ListarItensDto listarItensDto = new ListarItensDto();

        listarItensDto.setDescricao( itemDeCardapio.getDescricao() );
        listarItensDto.setId( itemDeCardapio.getId() );
        listarItensDto.setNome( itemDeCardapio.getNome() );
        listarItensDto.setPreco( itemDeCardapio.getPreco() );

        return listarItensDto;
    }

    @Override
    public List<ListarItensDto> toDtoList(List<ItemDeCardapio> itemsDeCardapio) {
        if ( itemsDeCardapio == null ) {
            return null;
        }

        List<ListarItensDto> list = new ArrayList<ListarItensDto>( itemsDeCardapio.size() );
        for ( ItemDeCardapio itemDeCardapio : itemsDeCardapio ) {
            list.add( toDto( itemDeCardapio ) );
        }

        return list;
    }
}
