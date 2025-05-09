package com.example.demo.mapper;

import com.example.demo.dto.CadastrarItensDto;
import com.example.demo.dto.ListarItensDto;
import com.example.demo.entities.ItemDeCardapio;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-09T09:32:35-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
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
    public ListarItensDto tDto(ItemDeCardapio itemDeCardapio) {
        if ( itemDeCardapio == null ) {
            return null;
        }

        ListarItensDto listarItensDto = new ListarItensDto();

        listarItensDto.setDescricao( itemDeCardapio.getDescricao() );
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
            list.add( tDto( itemDeCardapio ) );
        }

        return list;
    }
}
