package com.example.demo.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.dto.PedidoDto.PedidoExportacaoCsvDto;
import com.example.demo.entities.Pedido;
import com.example.demo.mapper.PedidoMapper;
import com.example.demo.repository.IPedidoItemRepository;
import com.example.demo.repository.IPedidoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private IPedidoItemRepository pedidoItemRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Transactional
    public ListarPedidoDto salvar(CadastrarPedidoDto pedidoDto) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDto);
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    public List<ListarPedidoDto> listarPedidos() {
        return pedidoMapper.toListDto(pedidoRepository.findAll());
    }

    public List<ListarPedidoDto> listarPedidoPorReserva(Long id) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getReserva().getId().equals(id))
                .map(pedidoMapper::toDto)
                .toList();
    }

    public ListarPedidoDto obterPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        return pedidoMapper.toDto(pedido);
    }

    @Transactional
    public void removerPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }

        pedidoItemRepository.deleteAllByPedido_Id(id);

        pedidoRepository.deleteById(id);
    }

    public List<PedidoExportacaoCsvDto> convertePedidosCsv() {
        return listarPedidos().stream()
                .flatMap(pedido -> pedido.getPedidos().stream().map(item -> new PedidoExportacaoCsvDto(
                        pedido.getNumeroMesa(),
                        pedido.getDataReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        pedido.getHoraReserva().format(DateTimeFormatter.ofPattern("HH:mm")),
                        pedido.getNomeCliente(),
                        item.getNomeItem(),
                        item.getQuantidade(),
                        item.getSubTotal(),
                        pedido.getValorTotal())))
                .toList();
    }
}
