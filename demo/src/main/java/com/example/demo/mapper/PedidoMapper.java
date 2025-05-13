package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusReserva;
import com.example.demo.mapper.Utils.PedidoMapperHelper;

@Mapper(componentModel = "spring", uses = PedidoMapperHelper.class)
public interface PedidoMapper {

    @Mapping(source = "reserva_id", target = "reserva", qualifiedByName = "buscaReservaPorId")
    @Mapping(source = "pedidos", target = "pedidoItens", qualifiedByName = "preparaPedidos")
    @Mapping(source = "pedidos", target = "valorTotal", qualifiedByName = "calculaTotal")
    Pedido toEntity(CadastrarPedidoDto pedidoDto);

    @Mapping(target = "numeroMesa", source = "reserva.mesa.numero")
    @Mapping(target = "dataReserva", source = "reserva.dataReserva")
    @Mapping(target = "horaReserva", source = "reserva.horaReserva")
    @Mapping(target = "nomeCliente", source = "reserva.cliente.nome")
    @Mapping(source = "pedidoItens", target = "pedidos", qualifiedByName = "convertePedidosDto")
    @Mapping(target = "valorTotal", source = "valorTotal")
    ListarPedidoDto toDto(Pedido pedido);

    List<ListarPedidoDto> toListDto(List<Pedido> pedidos);

    @AfterMapping
    default void verificaStatusReserva(@MappingTarget Pedido pedido) {
        Reserva reserva = pedido.getReserva();

        if (reserva.getStatus().equals(StatusReserva.CONCLUIDA)
                || reserva.getStatus().equals(StatusReserva.CANCELADA)) {
            throw new IllegalStateException("Uma reserva concluida ou cancelada não pode fazer pedidos.");
        }
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido, CadastrarPedidoDto pedidoDto) {
        if (pedido.getPedidoItens() != null) {
            pedido.getPedidoItens().forEach(p -> p.setPedido(pedido));
        }
    }
}
