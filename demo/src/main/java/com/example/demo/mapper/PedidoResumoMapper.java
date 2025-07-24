package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.PedidoDto.ItemResumoDto;
import com.example.demo.dto.PedidoDto.PedidoDetalhadoDto;
import com.example.demo.dto.PedidoDto.PedidoResumoDto;
import com.example.demo.entities.Pedido;
import com.example.demo.mapper.Utils.PedidoMapperHelper;

@Mapper(componentModel = "spring", uses = PedidoMapperHelper.class)
public interface PedidoResumoMapper {

    @Mapping(target = "numeroMesa", source = "reserva.mesa.numero")
    @Mapping(target = "dataReserva", source = "reserva.dataReserva")
    @Mapping(target = "horaReserva", source = "reserva.horaReserva")
    @Mapping(target = "nomeCliente", source = "reserva.cliente.nome")
    @Mapping(target = "nomeFuncionario", source = "funcionario.nome")
    @Mapping(target = "totalItens", expression = "java(pedido.getPedidoItens().stream().mapToInt(item -> item.getQuantidade()).sum())")
    @Mapping(target = "status", source = "status")
    PedidoResumoDto toResumoDto(Pedido pedido);

    @Mapping(target = "numeroMesa", source = "reserva.mesa.numero")
    @Mapping(target = "dataReserva", source = "reserva.dataReserva")
    @Mapping(target = "horaReserva", source = "reserva.horaReserva")
    @Mapping(target = "nomeCliente", source = "reserva.cliente.nome")
    @Mapping(target = "nomeFuncionario", source = "funcionario.nome")
    @Mapping(source = "pedidoItens", target = "itens", qualifiedByName = "converteItensResumo")
    @Mapping(target = "observacoes", source = "reserva.cliente.observacoes")
    @Mapping(target = "status", source = "status")
    PedidoDetalhadoDto toDetalhadoDto(Pedido pedido);

    List<PedidoResumoDto> toListResumoDto(List<Pedido> pedidos);
} 