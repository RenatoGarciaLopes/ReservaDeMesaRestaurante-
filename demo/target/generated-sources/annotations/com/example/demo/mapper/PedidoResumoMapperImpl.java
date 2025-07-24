package com.example.demo.mapper;

import com.example.demo.dto.PedidoDto.PedidoDetalhadoDto;
import com.example.demo.dto.PedidoDto.PedidoResumoDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Reserva;
import com.example.demo.mapper.Utils.PedidoMapperHelper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-24T19:07:33-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class PedidoResumoMapperImpl implements PedidoResumoMapper {

    @Autowired
    private PedidoMapperHelper pedidoMapperHelper;

    @Override
    public PedidoResumoDto toResumoDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoResumoDto pedidoResumoDto = new PedidoResumoDto();

        pedidoResumoDto.setNumeroMesa( pedidoReservaMesaNumero( pedido ) );
        pedidoResumoDto.setDataReserva( pedidoReservaDataReserva( pedido ) );
        pedidoResumoDto.setHoraReserva( pedidoReservaHoraReserva( pedido ) );
        pedidoResumoDto.setNomeCliente( pedidoReservaClienteNome( pedido ) );
        pedidoResumoDto.setNomeFuncionario( pedidoFuncionarioNome( pedido ) );
        if ( pedido.getStatus() != null ) {
            pedidoResumoDto.setStatus( pedido.getStatus().name() );
        }
        pedidoResumoDto.setId( pedido.getId() );
        pedidoResumoDto.setValorTotal( pedido.getValorTotal() );

        pedidoResumoDto.setTotalItens( pedido.getPedidoItens().stream().mapToInt(item -> item.getQuantidade()).sum() );

        return pedidoResumoDto;
    }

    @Override
    public PedidoDetalhadoDto toDetalhadoDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoDetalhadoDto pedidoDetalhadoDto = new PedidoDetalhadoDto();

        pedidoDetalhadoDto.setNumeroMesa( pedidoReservaMesaNumero( pedido ) );
        pedidoDetalhadoDto.setDataReserva( pedidoReservaDataReserva( pedido ) );
        pedidoDetalhadoDto.setHoraReserva( pedidoReservaHoraReserva( pedido ) );
        pedidoDetalhadoDto.setNomeCliente( pedidoReservaClienteNome( pedido ) );
        pedidoDetalhadoDto.setNomeFuncionario( pedidoFuncionarioNome( pedido ) );
        pedidoDetalhadoDto.setItens( pedidoMapperHelper.converteItensResumo( pedido.getPedidoItens() ) );
        pedidoDetalhadoDto.setObservacoes( pedidoReservaClienteObservacoes( pedido ) );
        if ( pedido.getStatus() != null ) {
            pedidoDetalhadoDto.setStatus( pedido.getStatus().name() );
        }
        pedidoDetalhadoDto.setId( pedido.getId() );
        pedidoDetalhadoDto.setValorTotal( pedido.getValorTotal() );

        return pedidoDetalhadoDto;
    }

    @Override
    public List<PedidoResumoDto> toListResumoDto(List<Pedido> pedidos) {
        if ( pedidos == null ) {
            return null;
        }

        List<PedidoResumoDto> list = new ArrayList<PedidoResumoDto>( pedidos.size() );
        for ( Pedido pedido : pedidos ) {
            list.add( toResumoDto( pedido ) );
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

    private String pedidoFuncionarioNome(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Funcionario funcionario = pedido.getFuncionario();
        if ( funcionario == null ) {
            return null;
        }
        String nome = funcionario.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String pedidoReservaClienteObservacoes(Pedido pedido) {
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
        String observacoes = cliente.getObservacoes();
        if ( observacoes == null ) {
            return null;
        }
        return observacoes;
    }
}
