package com.example.demo.mapper;

import com.example.demo.dto.CadastrarMesaDto;
import com.example.demo.dto.ListarMesaDto;
import com.example.demo.entities.Mesa;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T16:48:10-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class MesaMapperImpl implements MesaMapper {

    @Override
    public Mesa toEntity(CadastrarMesaDto mesaDto) {
        if ( mesaDto == null ) {
            return null;
        }

        Mesa mesa = new Mesa();

        mesa.setCapacidade( mesaDto.getCapacidade() );
        mesa.setNumero( mesaDto.getNumero() );

        return mesa;
    }

    @Override
    public ListarMesaDto toDto(Mesa mesa) {
        if ( mesa == null ) {
            return null;
        }

        ListarMesaDto listarMesaDto = new ListarMesaDto();

        listarMesaDto.setCapacidade( mesa.getCapacidade() );
        listarMesaDto.setNumero( mesa.getNumero() );
        listarMesaDto.setStatus( mesa.getStatus() );

        return listarMesaDto;
    }

    @Override
    public List<ListarMesaDto> toDtoLIst(List<Mesa> mesas) {
        if ( mesas == null ) {
            return null;
        }

        List<ListarMesaDto> list = new ArrayList<ListarMesaDto>( mesas.size() );
        for ( Mesa mesa : mesas ) {
            list.add( toDto( mesa ) );
        }

        return list;
    }
}
