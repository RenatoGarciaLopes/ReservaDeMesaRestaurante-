package com.example.demo.mapper;

import com.example.demo.dto.CategoriaDto.CadastrarCategoriaDto;
import com.example.demo.dto.CategoriaDto.ListarCategoriaDto;
import com.example.demo.entities.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-26T13:45:08-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CategoriaMapperImpl implements CategoriaMapper {

    @Override
    public Categoria toEntity(CadastrarCategoriaDto dto) {
        if ( dto == null ) {
            return null;
        }

        Categoria categoria = new Categoria();

        categoria.setNome( dto.getNome() );

        return categoria;
    }

    @Override
    public ListarCategoriaDto toDto(Categoria entity) {
        if ( entity == null ) {
            return null;
        }

        ListarCategoriaDto listarCategoriaDto = new ListarCategoriaDto();

        listarCategoriaDto.setId( entity.getId() );
        listarCategoriaDto.setNome( entity.getNome() );

        return listarCategoriaDto;
    }

    @Override
    public List<ListarCategoriaDto> toListDto(List<Categoria> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ListarCategoriaDto> list = new ArrayList<ListarCategoriaDto>( entities.size() );
        for ( Categoria categoria : entities ) {
            list.add( toDto( categoria ) );
        }

        return list;
    }
}
