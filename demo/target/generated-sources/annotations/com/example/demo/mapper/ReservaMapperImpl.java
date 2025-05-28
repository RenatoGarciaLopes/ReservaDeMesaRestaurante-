package com.example.demo.mapper;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Reserva;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-27T22:28:38-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservaMapperImpl implements ReservaMapper {

    @Override
    public Reserva toEntity(CadastrarReservaDTO reservaDTO) {
        if ( reservaDTO == null ) {
            return null;
        }

        Reserva reserva = new Reserva();

        reserva.setDataReserva( reservaDTO.getDataReserva() );
        reserva.setHoraReserva( reservaDTO.getHoraReserva() );

        return reserva;
    }

    @Override
    public ListarReservaDto toDto(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }

        ListarReservaDto listarReservaDto = new ListarReservaDto();

        listarReservaDto.setDataReserva( reserva.getDataReserva() );
        listarReservaDto.setHoraReserva( reserva.getHoraReserva() );
        listarReservaDto.setStatus( reserva.getStatus() );

        return listarReservaDto;
    }

    @Override
    public List<ListarReservaDto> toListDtos(List<Reserva> reservas) {
        if ( reservas == null ) {
            return null;
        }

        List<ListarReservaDto> list = new ArrayList<ListarReservaDto>( reservas.size() );
        for ( Reserva reserva : reservas ) {
            list.add( toDto( reserva ) );
        }

        return list;
    }
}
