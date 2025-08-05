package com.example.demo.mapper;

import com.example.demo.dto.MesaDto.CadastrarMesaDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Reserva;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T16:32:20-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
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
        listarMesaDto.setId( mesa.getId() );
        listarMesaDto.setNumero( mesa.getNumero() );
        List<Reserva> list = mesa.getReservas();
        if ( list != null ) {
            listarMesaDto.setReservas( new ArrayList<Reserva>( list ) );
        }
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
