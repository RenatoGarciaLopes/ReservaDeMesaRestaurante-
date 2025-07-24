package com.example.demo.service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PedidoDto.CadastrarPedidoDto;
import com.example.demo.dto.PedidoDto.ListarPedidoDto;
import com.example.demo.dto.PedidoDto.PedidoDetalhadoDto;
import com.example.demo.dto.PedidoDto.PedidoResumoDto;
import com.example.demo.dto.PedidoDto.EstatisticasReservaDto;
import com.example.demo.dto.PedidoDto.PedidoExportacaoCsvDto;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusPedido;
import com.example.demo.enums.StatusReserva;
import com.example.demo.mapper.PedidoMapper;
import com.example.demo.mapper.PedidoResumoMapper;
import com.example.demo.repository.IPedidoRepository;
import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.specification.PedidoSpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoResumoMapper pedidoResumoMapper;

    @Transactional
    public ListarPedidoDto salvar(CadastrarPedidoDto pedidoDto) {
        Reserva reserva = reservaRepository.findById(pedidoDto.getReservaId())
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        if (reserva.getStatus().equals(StatusReserva.CONCLUIDA)
                || reserva.getStatus().equals(StatusReserva.CANCELADA)) {
            throw new IllegalStateException("Uma reserva concluída ou cancelada não pode ser relacionada a um pedido.");
        }

        Pedido pedido = pedidoMapper.toEntity(pedidoDto);
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    public Page<ListarPedidoDto> listarPedidos(int pagina, int tamanho,
            StatusPedido status, String nomeItem) {
        Specification<Pedido> spec = Specification.where(PedidoSpecification.temStatus(status))
                .and(PedidoSpecification.temItemDeCardapio(nomeItem));

        Pageable pageable = PageRequest.of(pagina, tamanho,
                Sort.by("reserva.dataReserva").and(Sort.by("reserva.horaReserva")));

        return pedidoRepository.findAll(spec, pageable).map(pedidoMapper::toDto);
    }

    public Page<ListarPedidoDto> listarPedidoPorReserva(int pagina, int tamanho, Long id) {
        Pageable pageable = PageRequest.of(pagina, tamanho,
                Sort.by("reserva.dataReserva").and(Sort.by("reserva.horaReserva")));

        return pedidoRepository.findByReservaId(id, pageable).map(pedidoMapper::toDto);
    }

    public Page<PedidoResumoDto> listarPedidoResumoPorReserva(int pagina, int tamanho, Long id) {
        Pageable pageable = PageRequest.of(pagina, tamanho,
                Sort.by("reserva.dataReserva").and(Sort.by("reserva.horaReserva")));

        return pedidoRepository.findByReservaId(id, pageable).map(pedidoResumoMapper::toResumoDto);
    }

    public Page<PedidoDetalhadoDto> listarPedidoDetalhadoPorReserva(int pagina, int tamanho, Long id) {
        Pageable pageable = PageRequest.of(pagina, tamanho,
                Sort.by("reserva.dataReserva").and(Sort.by("reserva.horaReserva")));

        return pedidoRepository.findByReservaId(id, pageable).map(pedidoResumoMapper::toDetalhadoDto);
    }

    public EstatisticasReservaDto obterEstatisticasReserva(Long reservaId) {
        List<Pedido> pedidos = pedidoRepository.findByReservaId(reservaId, Pageable.unpaged()).getContent();
        
        if (pedidos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum pedido encontrado para a reserva");
        }

        Pedido primeiroPedido = pedidos.get(0);
        Reserva reserva = primeiroPedido.getReserva();

        EstatisticasReservaDto estatisticas = new EstatisticasReservaDto();
        estatisticas.setReservaId(reservaId);
        estatisticas.setNumeroMesa(reserva.getMesa().getNumero());
        estatisticas.setDataReserva(reserva.getDataReserva());
        estatisticas.setHoraReserva(reserva.getHoraReserva());
        estatisticas.setNomeCliente(reserva.getCliente().getNome());
        estatisticas.setTotalPedidos(pedidos.size());
        estatisticas.setStatusReserva(reserva.getStatus().toString());

        int totalItens = pedidos.stream()
                .flatMapToInt(pedido -> pedido.getPedidoItens().stream()
                        .mapToInt(item -> item.getQuantidade()))
                .sum();

        BigDecimal valorTotal = pedidos.stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        estatisticas.setTotalItens(totalItens);
        estatisticas.setValorTotal(valorTotal);

        return estatisticas;
    }

    public ListarPedidoDto obterPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não foi encontrado"));

        return pedidoMapper.toDto(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não foi encontrado"));

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    public List<PedidoExportacaoCsvDto> convertePedidosCsv() {
        Stream<PedidoExportacaoCsvDto> pedidos = pedidoRepository.findAll().stream()
                .flatMap(pedido -> pedido.getPedidoItens().stream().map(item -> new PedidoExportacaoCsvDto(
                        pedido.getReserva().getMesa().getNumero(),
                        pedido.getReserva().getDataReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        pedido.getReserva().getHoraReserva().format(DateTimeFormatter.ofPattern("HH:mm")),
                        pedido.getReserva().getCliente().getNome(),
                        item.getItem().getNome(),
                        item.getQuantidade(),
                        item.getItem().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())),
                        pedido.getValorTotal())));

        return pedidos.toList();
    }
}
