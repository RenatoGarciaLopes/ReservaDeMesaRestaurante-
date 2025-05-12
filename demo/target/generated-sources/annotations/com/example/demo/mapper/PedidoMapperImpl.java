package com.example.demo.mapper;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Reserva;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-12T20:34:17-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class PedidoMapperImpl extends PedidoMapper {

    @Override
    public Pedido toEntity(CadastrarPedidoDto pedidoDto) {
        if ( pedidoDto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setPedidoItens( preparaPedidos( pedidoDto.getPedidos() ) );
        pedido.setValorTotal( calculaTotal( pedidoDto.getPedidos() ) );

        pedido.setReserva( reservaRepository.getReferenceById(pedidoDto.getReserva_id()) );

        verificaStatusReserva( pedido );
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
        listarPedidoDto.setDataReserva( pedidoReservaDataReserva( pedido ) );
        listarPedidoDto.setHoraReserva( pedidoReservaHoraReserva( pedido ) );
        listarPedidoDto.setNomeCliente( pedidoReservaClienteNome( pedido ) );
        listarPedidoDto.setPedidos( convertePedidos( pedido.getPedidoItens() ) );
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

    private LocalDateTime pedidoReservaDataReserva(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Reserva reserva = pedido.getReserva();
        if ( reserva == null ) {
            return null;
        }
        LocalDateTime dataReserva = reserva.getDataReserva();
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
