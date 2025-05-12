package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.entities.Pedido;
import com.example.demo.mapper.PedidoMapper;
import com.example.demo.repository.IPedidoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

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
}
