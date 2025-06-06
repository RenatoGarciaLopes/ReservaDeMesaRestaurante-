package com.example.demo.mapper;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Reserva;
import com.example.demo.mapper.Utils.ReservaMapperHelper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-06T07:28:39-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservaMapperImpl implements ReservaMapper {

    @Autowired
    private ReservaMapperHelper reservaMapperHelper;
    private final DatatypeFactory datatypeFactory;

    public ReservaMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Reserva toEntity(CadastrarReservaDTO reservaDTO) {
        if ( reservaDTO == null ) {
            return null;
        }

        Reserva reserva = new Reserva();

        reserva.setCliente( reservaMapperHelper.buscarCliente( reservaDTO.getClienteId() ) );
        reserva.setMesa( reservaMapperHelper.buscarMesa( reservaDTO.getMesaId() ) );
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

        listarReservaDto.setNomeCliente( reservaClienteNome( reserva ) );
        listarReservaDto.setNumeroMesa( reservaMesaNumero( reserva ) );
        listarReservaDto.setDataReserva( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( reserva.getDataReserva() ) ) );
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

    private XMLGregorianCalendar localDateToXmlGregorianCalendar( LocalDate localDate ) {
        if ( localDate == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    private String reservaClienteNome(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Cliente cliente = reserva.getCliente();
        if ( cliente == null ) {
            return null;
        }
        String nome = cliente.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private Integer reservaMesaNumero(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Mesa mesa = reserva.getMesa();
        if ( mesa == null ) {
            return null;
        }
        Integer numero = mesa.getNumero();
        if ( numero == null ) {
            return null;
        }
        return numero;
    }
}
