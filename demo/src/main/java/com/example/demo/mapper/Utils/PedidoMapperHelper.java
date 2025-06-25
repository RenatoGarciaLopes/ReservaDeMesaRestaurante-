package com.example.demo.mapper.Utils;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.PedidoDto.ItemQuantidadeDto;
import com.example.demo.dto.PedidoDto.ListarItemPedidoDto;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.ItemDeCardapio;
import com.example.demo.entities.PedidoItem;
import com.example.demo.entities.Reserva;
import com.example.demo.repository.IFuncionarioRepository;
import com.example.demo.repository.IItemDeCardapioRepository;
import com.example.demo.repository.IReservaRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class PedidoMapperHelper {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IFuncionarioRepository funcionarioRepository;

    @Autowired
    protected IItemDeCardapioRepository itemDeCardapioRepository;

    @Named("buscaReserva")
    public Reserva buscaReserva(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
    }

    @Named("BuscarFuncionario")
    public Funcionario buscarFuncionario(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
    }

    @Named("preparaPedidos")
    public List<PedidoItem> preparaPedidos(List<ItemQuantidadeDto> itemQuantidadeDto) {
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
    public BigDecimal calculaTotal(List<ItemQuantidadeDto> itemQuantidadeDto) {
        return itemQuantidadeDto.stream()
                .map(dto -> {
                    ItemDeCardapio item = itemDeCardapioRepository.findById(dto.getItemId())
                            .orElseThrow(() -> new EntityNotFoundException("Item de cardápio não encontrado."));

                    return (BigDecimal) item.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Named("convertePedidosDto")
    public List<ListarItemPedidoDto> convertePedidos(List<PedidoItem> pedidos) {
        return pedidos.stream()
                .map(pedido -> {
                    ListarItemPedidoDto dto = new ListarItemPedidoDto();

                    dto.setNomeItem(pedido.getItem().getNome());
                    dto.setQuantidade(pedido.getQuantidade());
                    dto.setImagemUrl(pedido.getItem().getImagemUrl());
                    dto.setCategoria(pedido.getItem().getCategoria().getNome());
                    dto.setSubTotal(pedido.getItem().getPreco().multiply(BigDecimal.valueOf(pedido.getQuantidade())));

                    return dto;
                }).toList();
    }
}
