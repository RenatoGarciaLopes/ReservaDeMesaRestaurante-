package com.example.demo.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ItemQuantidadeDto;
import com.example.demo.dto.PedidoDto.ListarItemPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.entities.ItemDeCardapio;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.PedidoItem;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusReserva;
import com.example.demo.repository.IItemDeCardapioRepository;
import com.example.demo.repository.IReservaRepository;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class PedidoMapper {

    @Autowired
    protected IReservaRepository reservaRepository;

    @Autowired
    protected IItemDeCardapioRepository itemDeCardapioRepository;

    @Mapping(target = "reserva", expression = "java(reservaRepository.getReferenceById(pedidoDto.getReserva_id()))")
    @Mapping(source = "pedidos", target = "pedidoItens", qualifiedByName = "preparaPedidos")
    @Mapping(source = "pedidos", target = "valorTotal", qualifiedByName = "calculaTotal")
    abstract public Pedido toEntity(CadastrarPedidoDto pedidoDto);

    @Mapping(target = "numeroMesa", source = "reserva.mesa.numero")
    @Mapping(target = "dataReserva", source = "reserva.dataReserva")
    @Mapping(target = "horaReserva", source = "reserva.horaReserva")
    @Mapping(target = "nomeCliente", source = "reserva.cliente.nome")
    @Mapping(source = "pedidoItens", target = "pedidos", qualifiedByName = "convertePedidos")
    @Mapping(target = "valorTotal", source = "valorTotal")
    abstract public ListarPedidoDto toDto(Pedido pedido);

    abstract public List<ListarPedidoDto> toListDto(List<Pedido> pedidos);

    @Named("preparaPedidos")
    protected List<PedidoItem> preparaPedidos(List<ItemQuantidadeDto> itemQuantidadeDto) {
        return itemQuantidadeDto.stream()
                .map(dto -> {
                    ItemDeCardapio item = itemDeCardapioRepository.findById(dto.getItemId())
                            .orElseThrow(() -> new EntityNotFoundException("Item de cardápio não encontrado."));

                    PedidoItem pedido = new PedidoItem();
                    pedido.setItem(item);
                    pedido.setQuantidade(dto.getQuantidade());

                    return pedido;
                }).toList();
    }

    @Named("calculaTotal")
    protected BigDecimal calculaTotal(List<ItemQuantidadeDto> itemQuantidadeDto) {
        return itemQuantidadeDto.stream()
                .map(dto -> {
                    ItemDeCardapio item = itemDeCardapioRepository.findById(dto.getItemId())
                            .orElseThrow(() -> new EntityNotFoundException("Item de cardápio não encontrado."));

                    return item.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @AfterMapping
    protected void verificaStatusReserva(@MappingTarget Pedido pedido) {
        Reserva reserva = pedido.getReserva();

        if (reserva.getStatus().equals(StatusReserva.CONCLUIDA)
                || reserva.getStatus().equals(StatusReserva.CANCELADA)) {
            throw new IllegalStateException("Uma reserva concluida ou cancelada não pode fazer pedidos.");
        }
    }

    @AfterMapping
    protected void afterMapping(@MappingTarget Pedido pedido, CadastrarPedidoDto pedidoDto) {
        if (pedido.getPedidoItens() != null) {
            pedido.getPedidoItens().forEach(p -> p.setPedido(pedido));
        }
    }

    @Named("convertePedidos")
    protected List<ListarItemPedidoDto> convertePedidos(List<PedidoItem> pedidos) {
        return pedidos.stream()
                .map(pedido -> {
                    ListarItemPedidoDto dto = new ListarItemPedidoDto();

                    dto.setNomeItem(pedido.getItem().getNome());
                    dto.setQuantidade(pedido.getQuantidade());
                    dto.setSubTotal(pedido.getItem().getPreco().multiply(BigDecimal.valueOf(pedido.getQuantidade())));

                    return dto;
                }).toList();
    }
}
