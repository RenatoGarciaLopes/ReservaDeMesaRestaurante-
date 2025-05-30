package com.example.demo.mapper;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Reserva;
import com.example.demo.mapper.Utils.PedidoMapperHelper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    date = "2025-05-30T16:49:00-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Autowired
    private PedidoMapperHelper pedidoMapperHelper;
    private final DatatypeFactory datatypeFactory;

    public PedidoMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Pedido toEntity(CadastrarPedidoDto pedidoDto) {
        if ( pedidoDto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setReserva( pedidoMapperHelper.buscaReservaPorId( pedidoDto.getReserva_id() ) );
        pedido.setPedidoItens( pedidoMapperHelper.preparaPedidos( pedidoDto.getPedidos() ) );
        pedido.setValorTotal( pedidoMapperHelper.calculaTotal( pedidoDto.getPedidos() ) );

        afterMapping( pedido, pedidoDto );

        return pedido;
    }

    @Override
    public ListarPedidoDto toDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        ListarPedidoDto listarPedidoDto = new ListarPedidoDto();

        listarPedidoDto.setNumeroMesa( pedidoReservaMesaNumero( pedido ) );
        listarPedidoDto.setDataReserva( xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( pedidoReservaDataReserva( pedido ) ) ) );
        listarPedidoDto.setHoraReserva( pedidoReservaHoraReserva( pedido ) );
        listarPedidoDto.setNomeCliente( pedidoReservaClienteNome( pedido ) );
        listarPedidoDto.setPedidos( pedidoMapperHelper.convertePedidos( pedido.getPedidoItens() ) );
        listarPedidoDto.setValorTotal( pedido.getValorTotal() );

        return listarPedidoDto;
    }

    @Override
    public List<ListarPedidoDto> toListDto(List<Pedido> pedidos) {
        if ( pedidos == null ) {
            return null;
        }

        List<ListarPedidoDto> list = new ArrayList<ListarPedidoDto>( pedidos.size() );
        for ( Pedido pedido : pedidos ) {
            list.add( toDto( pedido ) );
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

    private Integer pedidoReservaMesaNumero(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Reserva reserva = pedido.getReserva();
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

    private LocalDate pedidoReservaDataReserva(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Reserva reserva = pedido.getReserva();
        if ( reserva == null ) {
            return null;
        }
        LocalDate dataReserva = reserva.getDataReserva();
        if ( dataReserva == null ) {
            return null;
        }
        return dataReserva;
    }

    private LocalTime pedidoReservaHoraReserva(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Reserva reserva = pedido.getReserva();
        if ( reserva == null ) {
            return null;
        }
        LocalTime horaReserva = reserva.getHoraReserva();
        if ( horaReserva == null ) {
            return null;
        }
        return horaReserva;
    }

    private String pedidoReservaClienteNome(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Reserva reserva = pedido.getReserva();
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
}
