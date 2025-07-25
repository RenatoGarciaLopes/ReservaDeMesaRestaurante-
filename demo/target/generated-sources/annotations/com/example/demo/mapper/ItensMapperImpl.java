package com.example.demo.mapper;

import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.entities.Categoria;
import com.example.demo.entities.ItemDeCardapio;
import com.example.demo.mapper.Utils.ItemHelperMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-24T22:45:21-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ItensMapperImpl implements ItensMapper {

    @Autowired
    private ItemHelperMapper itemHelperMapper;

    @Override
    public ItemDeCardapio toEntity(CadastrarItensDto itensDto) {
        if ( itensDto == null ) {
            return null;
        }

        ItemDeCardapio itemDeCardapio = new ItemDeCardapio();

        itemDeCardapio.setCategoria( itemHelperMapper.buscaReservaPorId( itensDto.getCategoriaId() ) );
        itemDeCardapio.setDescricao( itensDto.getDescricao() );
        itemDeCardapio.setImagemUrl( itensDto.getImagemUrl() );
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

        listarItensDto.setCategoria( itemDeCardapioCategoriaNome( itemDeCardapio ) );
        listarItensDto.setDescricao( itemDeCardapio.getDescricao() );
        listarItensDto.setId( itemDeCardapio.getId() );
        listarItensDto.setImagemUrl( itemDeCardapio.getImagemUrl() );
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

    private String itemDeCardapioCategoriaNome(ItemDeCardapio itemDeCardapio) {
        if ( itemDeCardapio == null ) {
            return null;
        }
        Categoria categoria = itemDeCardapio.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        String nome = categoria.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }
}
