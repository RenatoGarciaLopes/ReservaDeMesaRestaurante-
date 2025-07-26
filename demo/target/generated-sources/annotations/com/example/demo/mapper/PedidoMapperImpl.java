package com.example.demo.mapper;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.Cargo;
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
    date = "2025-07-26T13:45:08-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Autowired
    private PedidoMapperHelper pedidoMapperHelper;

    @Override
    public Pedido toEntity(CadastrarPedidoDto pedidoDto) {
        if ( pedidoDto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setReserva( pedidoMapperHelper.buscaReserva( pedidoDto.getReservaId() ) );
        pedido.setFuncionario( pedidoMapperHelper.buscarFuncionario( pedidoDto.getFuncionarioId() ) );
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
        listarPedidoDto.setDataReserva( pedidoReservaDataReserva( pedido ) );
        listarPedidoDto.setHoraReserva( pedidoReservaHoraReserva( pedido ) );
        listarPedidoDto.setNomeCliente( pedidoReservaClienteNome( pedido ) );
        listarPedidoDto.setNomeFuncionario( pedidoFuncionarioNome( pedido ) );
        listarPedidoDto.setCargo( pedidoFuncionarioCargo( pedido ) );
        listarPedidoDto.setPedidos( pedidoMapperHelper.convertePedidos( pedido.getPedidoItens() ) );
        listarPedidoDto.setObservacoes( pedidoReservaClienteObservacoes( pedido ) );
        listarPedidoDto.setValorTotal( pedido.getValorTotal() );
        listarPedidoDto.setStatus( pedido.getStatus() );
        listarPedidoDto.setId( pedido.getId() );

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

    private Cargo pedidoFuncionarioCargo(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }
        Funcionario funcionario = pedido.getFuncionario();
        if ( funcionario == null ) {
            return null;
        }
        Cargo cargo = funcionario.getCargo();
        if ( cargo == null ) {
            return null;
        }
        return cargo;
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
