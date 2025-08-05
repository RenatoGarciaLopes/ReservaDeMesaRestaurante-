package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EstatisticasDto.EstatisticasGeraisDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasReservasDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasPedidosDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasFuncionariosDto;
import com.example.demo.dto.EstatisticasDto.ReservaPorPeriodoDto;
import com.example.demo.dto.EstatisticasDto.PedidoPorPeriodoDto;
import com.example.demo.dto.EstatisticasDto.ItemMaisVendidoDto;
import com.example.demo.dto.EstatisticasDto.FuncionarioPerformanceDto;
import com.example.demo.dto.EstatisticasDto.FuncionarioFaturamentoDto;
import com.example.demo.dto.EstatisticasDto.EstatisticasExportacaoCsvDto;
import com.example.demo.entities.Reserva;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mesa;
import com.example.demo.enums.StatusReserva;
import com.example.demo.enums.StatusPedido;
import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.IPedidoRepository;
import com.example.demo.repository.IFuncionarioRepository;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IMesaRepository;

@Service
public class EstatisticasService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private IFuncionarioRepository funcionarioRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IMesaRepository mesaRepository;

    public EstatisticasGeraisDto obterEstatisticasGerais(LocalDate dataInicio, LocalDate dataFim) {
        EstatisticasGeraisDto estatisticas = new EstatisticasGeraisDto();
        estatisticas.setDataInicio(dataInicio);
        estatisticas.setDataFim(dataFim);

        // Total de reservas no período
        List<Reserva> reservas = reservaRepository.findByDataReservaBetween(dataInicio, dataFim);
        estatisticas.setTotalReservas((long) reservas.size());

        // Total de pedidos no período
        List<Pedido> pedidos = pedidoRepository.findByReservaDataReservaBetween(dataInicio, dataFim);
        estatisticas.setTotalPedidos((long) pedidos.size());

        // Total de clientes
        estatisticas.setTotalClientes(clienteRepository.countByAtivoTrue());

        // Total de funcionários ativos
        estatisticas.setTotalFuncionarios(funcionarioRepository.countByAtivoTrue());

        // Faturamento total
        BigDecimal faturamentoTotal = pedidos.stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        estatisticas.setFaturamentoTotal(faturamentoTotal);

        // Ticket médio
        if (!pedidos.isEmpty()) {
            BigDecimal ticketMedio = faturamentoTotal.divide(BigDecimal.valueOf(pedidos.size()), 2, RoundingMode.HALF_UP);
            estatisticas.setTicketMedio(ticketMedio);
        } else {
            estatisticas.setTicketMedio(BigDecimal.ZERO);
        }

        // Taxa de ocupação (mesas ocupadas vs. total de mesas)
        long totalMesas = mesaRepository.countByAtivoTrue();
        long mesasOcupadas = reservas.stream()
                .filter(r -> r.getStatus() == StatusReserva.ATIVA || r.getStatus() == StatusReserva.CONCLUIDA)
                .count();
        
        if (totalMesas > 0) {
            double taxaOcupacao = (double) mesasOcupadas / totalMesas * 100;
            estatisticas.setTaxaOcupacao(taxaOcupacao);
        } else {
            estatisticas.setTaxaOcupacao(0.0);
        }

        return estatisticas;
    }

    public EstatisticasReservasDto obterEstatisticasReservas(LocalDate dataInicio, LocalDate dataFim) {
        EstatisticasReservasDto estatisticas = new EstatisticasReservasDto();
        estatisticas.setDataInicio(dataInicio);
        estatisticas.setDataFim(dataFim);

        List<Reserva> reservas = reservaRepository.findByDataReservaBetween(dataInicio, dataFim);

        // Totais por status
        long reservasAtivas = reservas.stream().filter(r -> r.getStatus() == StatusReserva.ATIVA).count();
        long reservasCanceladas = reservas.stream().filter(r -> r.getStatus() == StatusReserva.CANCELADA).count();
        long reservasConcluidas = reservas.stream().filter(r -> r.getStatus() == StatusReserva.CONCLUIDA).count();

        estatisticas.setTotalReservas((long) reservas.size());
        estatisticas.setReservasAtivas(reservasAtivas);
        estatisticas.setReservasCanceladas(reservasCanceladas);
        estatisticas.setReservasConcluidas(reservasConcluidas);

        // Taxa de cancelamento
        if (!reservas.isEmpty()) {
            double taxaCancelamento = (double) reservasCanceladas / reservas.size() * 100;
            estatisticas.setTaxaCancelamento(taxaCancelamento);
        } else {
            estatisticas.setTaxaCancelamento(0.0);
        }

        // Média de pessoas por reserva
        if (!reservas.isEmpty()) {
            double mediaPessoas = reservas.stream()
                    .mapToInt(Reserva::getQuantidadePessoas)
                    .average()
                    .orElse(0.0);
            estatisticas.setMediaPessoasPorReserva(mediaPessoas);
        } else {
            estatisticas.setMediaPessoasPorReserva(0.0);
        }

        // Faturamento das reservas - usar query direta para evitar loops
        BigDecimal faturamentoReservas = pedidoRepository.findByReservaDataReservaBetween(dataInicio, dataFim)
                .stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        estatisticas.setFaturamentoReservas(faturamentoReservas);

        // Dados para gráficos
        estatisticas.setReservasPorDiaSemana(calcularReservasPorDiaSemana(reservas));
        estatisticas.setReservasPorHora(calcularReservasPorHora(reservas));
        estatisticas.setReservasPorMes(calcularReservasPorMes(reservas));
        estatisticas.setReservasPorPeriodo(calcularReservasPorPeriodo(reservas));

        return estatisticas;
    }

    public EstatisticasPedidosDto obterEstatisticasPedidos(LocalDate dataInicio, LocalDate dataFim) {
        EstatisticasPedidosDto estatisticas = new EstatisticasPedidosDto();
        estatisticas.setDataInicio(dataInicio);
        estatisticas.setDataFim(dataFim);

        List<Pedido> pedidos = pedidoRepository.findByReservaDataReservaBetween(dataInicio, dataFim);

        // Totais por status
        long pedidosRealizados = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.REALIZADO).count();
        long pedidosEntregues = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.ENTREGUE).count();
        long pedidosCancelados = pedidos.stream().filter(p -> p.getStatus() == StatusPedido.CANCELADO).count();

        estatisticas.setTotalPedidos((long) pedidos.size());
        estatisticas.setPedidosRealizados(pedidosRealizados);
        estatisticas.setPedidosEntregues(pedidosEntregues);
        estatisticas.setPedidosCancelados(pedidosCancelados);

        // Faturamento total
        BigDecimal faturamentoTotal = pedidos.stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        estatisticas.setFaturamentoTotal(faturamentoTotal);

        // Ticket médio
        if (!pedidos.isEmpty()) {
            BigDecimal ticketMedio = faturamentoTotal.divide(BigDecimal.valueOf(pedidos.size()), 2, RoundingMode.HALF_UP);
            estatisticas.setTicketMedio(ticketMedio);
        } else {
            estatisticas.setTicketMedio(BigDecimal.ZERO);
        }

        // Total de itens vendidos
        long totalItensVendidos = pedidos.stream()
                .flatMapToInt(p -> p.getPedidoItens().stream().mapToInt(item -> item.getQuantidade()))
                .sum();
        estatisticas.setTotalItensVendidos(totalItensVendidos);

        // Dados para gráficos
        estatisticas.setItensMaisVendidos(calcularItensMaisVendidos(pedidos));
        estatisticas.setPedidosPorStatus(calcularPedidosPorStatus(pedidos));
        estatisticas.setFaturamentoPorCategoria(calcularFaturamentoPorCategoria(pedidos));
        estatisticas.setPedidosPorPeriodo(calcularPedidosPorPeriodo(pedidos));

        return estatisticas;
    }

    public EstatisticasFuncionariosDto obterEstatisticasFuncionarios(LocalDate dataInicio, LocalDate dataFim) {
        EstatisticasFuncionariosDto estatisticas = new EstatisticasFuncionariosDto();
        estatisticas.setDataInicio(dataInicio);
        estatisticas.setDataFim(dataFim);

        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        // Totais
        long funcionariosAtivos = funcionarios.stream().filter(Funcionario::getAtivo).count();
        long funcionariosInativos = funcionarios.stream().filter(f -> !f.getAtivo()).count();

        estatisticas.setTotalFuncionarios((long) funcionarios.size());
        estatisticas.setFuncionariosAtivos(funcionariosAtivos);
        estatisticas.setFuncionariosInativos(funcionariosInativos);

        // Dados para gráficos
        estatisticas.setFuncionariosMaisAtivos(calcularFuncionariosMaisAtivos(dataInicio, dataFim));
        estatisticas.setFuncionariosMaiorFaturamento(calcularFuncionariosMaiorFaturamento(dataInicio, dataFim));

        return estatisticas;
    }

    public List<EstatisticasExportacaoCsvDto> converteEstatisticasCsv(LocalDate dataInicio, LocalDate dataFim) {
        List<Reserva> reservas = reservaRepository.findByDataReservaBetween(dataInicio, dataFim);
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return reservas.stream().map(reserva -> {
            // Buscar pedidos da reserva diretamente
            List<Pedido> pedidosReserva = pedidoRepository.findByReservaId(reserva.getId(), Pageable.unpaged()).getContent();
            int totalPedidos = pedidosReserva.size();
            
            int totalItens = pedidosReserva.stream()
                    .flatMapToInt(p -> p.getPedidoItens().stream().mapToInt(item -> item.getQuantidade()))
                    .sum();
            BigDecimal valorTotal = pedidosReserva.stream()
                    .map(Pedido::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            String statusPedidos = pedidosReserva.stream()
                    .map(p -> p.getStatus().toString())
                    .distinct()
                    .collect(Collectors.joining(", "));

            return new EstatisticasExportacaoCsvDto(
                    reserva.getDataReserva().format(dataFormatter),
                    reserva.getHoraReserva().format(horaFormatter),
                    reserva.getMesa().getNumero(),
                    reserva.getCliente().getNome(),
                    reserva.getFuncionario() != null ? reserva.getFuncionario().getNome() : "N/A",
                    reserva.getFuncionario() != null ? reserva.getFuncionario().getCargo().toString() : "N/A",
                    reserva.getQuantidadePessoas(),
                    reserva.getStatus().toString(),
                    totalPedidos,
                    totalItens,
                    valorTotal,
                    statusPedidos
            );
        }).collect(Collectors.toList());
    }

    // Métodos auxiliares para cálculos de gráficos
    private Map<String, Long> calcularReservasPorDiaSemana(List<Reserva> reservas) {
        Map<String, Long> reservasPorDia = new HashMap<>();
        String[] diasSemana = {"Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
        
        for (String dia : diasSemana) {
            reservasPorDia.put(dia, 0L);
        }

        reservas.forEach(reserva -> {
            String diaSemana = reserva.getDataReserva().getDayOfWeek().getValue() <= 7 
                ? diasSemana[reserva.getDataReserva().getDayOfWeek().getValue() - 1] 
                : "Domingo";
            reservasPorDia.merge(diaSemana, 1L, Long::sum);
        });

        return reservasPorDia;
    }

    private Map<String, Long> calcularReservasPorHora(List<Reserva> reservas) {
        Map<String, Long> reservasPorHora = new HashMap<>();
        
        for (int hora = 8; hora <= 23; hora++) {
            reservasPorHora.put(String.format("%02d:00", hora), 0L);
        }

        reservas.forEach(reserva -> {
            String hora = String.format("%02d:00", reserva.getHoraReserva().getHour());
            reservasPorHora.merge(hora, 1L, Long::sum);
        });

        return reservasPorHora;
    }

    private Map<String, Long> calcularReservasPorMes(List<Reserva> reservas) {
        return reservas.stream()
                .collect(Collectors.groupingBy(
                    r -> String.format("%d/%d", r.getDataReserva().getMonthValue(), r.getDataReserva().getYear()),
                    Collectors.counting()
                ));
    }

    private List<ReservaPorPeriodoDto> calcularReservasPorPeriodo(List<Reserva> reservas) {
        Map<LocalDate, List<Reserva>> reservasPorData = reservas.stream()
                .collect(Collectors.groupingBy(Reserva::getDataReserva));

        return reservasPorData.entrySet().stream()
                .map(entry -> {
                    LocalDate data = entry.getKey();
                    List<Reserva> reservasDoDia = entry.getValue();

                    ReservaPorPeriodoDto dto = new ReservaPorPeriodoDto();
                    dto.setData(data);
                    dto.setTotalReservas((long) reservasDoDia.size());
                    dto.setReservasAtivas(reservasDoDia.stream().filter(r -> r.getStatus() == StatusReserva.ATIVA).count());
                    dto.setReservasCanceladas(reservasDoDia.stream().filter(r -> r.getStatus() == StatusReserva.CANCELADA).count());
                    dto.setReservasConcluidas(reservasDoDia.stream().filter(r -> r.getStatus() == StatusReserva.CONCLUIDA).count());
                    
                    // Buscar pedidos das reservas do dia diretamente
                    BigDecimal faturamento = reservasDoDia.stream()
                            .flatMap(r -> pedidoRepository.findByReservaId(r.getId(), Pageable.unpaged()).getContent().stream())
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setFaturamento(faturamento);
                    
                    double mediaPessoas = reservasDoDia.stream()
                            .mapToInt(Reserva::getQuantidadePessoas)
                            .average()
                            .orElse(0.0);
                    dto.setMediaPessoas(mediaPessoas);

                    return dto;
                })
                .sorted(Comparator.comparing(ReservaPorPeriodoDto::getData))
                .collect(Collectors.toList());
    }

    private List<ItemMaisVendidoDto> calcularItensMaisVendidos(List<Pedido> pedidos) {
        Map<String, ItemMaisVendidoDto> itensMap = new HashMap<>();

        pedidos.forEach(pedido -> {
            pedido.getPedidoItens().forEach(item -> {
                String chave = item.getItem().getNome();
                ItemMaisVendidoDto dto = itensMap.getOrDefault(chave, new ItemMaisVendidoDto());
                
                dto.setNomeItem(item.getItem().getNome());
                dto.setCategoria(item.getItem().getCategoria() != null ? item.getItem().getCategoria().getNome() : "Sem categoria");
                dto.setPrecoUnitario(item.getItem().getPreco());
                dto.setQuantidadeVendida(dto.getQuantidadeVendida() != null ? dto.getQuantidadeVendida() + item.getQuantidade() : item.getQuantidade());
                
                BigDecimal valorTotal = item.getItem().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                dto.setValorTotalVendido(dto.getValorTotalVendido() != null ? dto.getValorTotalVendido().add(valorTotal) : valorTotal);
                
                itensMap.put(chave, dto);
            });
        });

        return itensMap.values().stream()
                .sorted(Comparator.comparing(ItemMaisVendidoDto::getQuantidadeVendida).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private Map<String, Long> calcularPedidosPorStatus(List<Pedido> pedidos) {
        return pedidos.stream()
                .collect(Collectors.groupingBy(
                    p -> p.getStatus().toString(),
                    Collectors.counting()
                ));
    }

    private Map<String, BigDecimal> calcularFaturamentoPorCategoria(List<Pedido> pedidos) {
        Map<String, BigDecimal> faturamentoPorCategoria = new HashMap<>();

        pedidos.forEach(pedido -> {
            pedido.getPedidoItens().forEach(item -> {
                String categoria = item.getItem().getCategoria() != null ? item.getItem().getCategoria().getNome() : "Sem categoria";
                BigDecimal valor = item.getItem().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                faturamentoPorCategoria.merge(categoria, valor, BigDecimal::add);
            });
        });

        return faturamentoPorCategoria;
    }

    private List<PedidoPorPeriodoDto> calcularPedidosPorPeriodo(List<Pedido> pedidos) {
        Map<LocalDate, List<Pedido>> pedidosPorData = pedidos.stream()
                .collect(Collectors.groupingBy(p -> p.getReserva().getDataReserva()));

        return pedidosPorData.entrySet().stream()
                .map(entry -> {
                    LocalDate data = entry.getKey();
                    List<Pedido> pedidosDoDia = entry.getValue();

                    PedidoPorPeriodoDto dto = new PedidoPorPeriodoDto();
                    dto.setData(data);
                    dto.setTotalPedidos((long) pedidosDoDia.size());
                    dto.setPedidosRealizados(pedidosDoDia.stream().filter(p -> p.getStatus() == StatusPedido.REALIZADO).count());
                    dto.setPedidosEntregues(pedidosDoDia.stream().filter(p -> p.getStatus() == StatusPedido.ENTREGUE).count());
                    dto.setPedidosCancelados(pedidosDoDia.stream().filter(p -> p.getStatus() == StatusPedido.CANCELADO).count());
                    
                    BigDecimal faturamento = pedidosDoDia.stream()
                            .map(Pedido::getValorTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setFaturamento(faturamento);
                    
                    long totalItens = pedidosDoDia.stream()
                            .flatMapToInt(p -> p.getPedidoItens().stream().mapToInt(item -> item.getQuantidade()))
                            .sum();
                    dto.setTotalItens(totalItens);

                    return dto;
                })
                .sorted(Comparator.comparing(PedidoPorPeriodoDto::getData))
                .collect(Collectors.toList());
    }

    private List<FuncionarioPerformanceDto> calcularFuncionariosMaisAtivos(LocalDate dataInicio, LocalDate dataFim) {
        List<Funcionario> funcionarios = funcionarioRepository.findByAtivoTrue();
        List<FuncionarioPerformanceDto> performance = new ArrayList<>();

        funcionarios.forEach(funcionario -> {
            // Pedidos processados pelo funcionário
            long totalPedidos = pedidoRepository.countByFuncionarioAndReservaDataReservaBetween(
                    funcionario, dataInicio, dataFim);
            
            // Reservas atendidas pelo funcionário
            long totalReservas = reservaRepository.countByFuncionarioAndDataReservaBetween(
                    funcionario, dataInicio, dataFim);

            // Calcular média de pedidos por dia
            long diasPeriodo = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
            double mediaPedidosPorDia = diasPeriodo > 0 ? (double) totalPedidos / diasPeriodo : 0.0;

            FuncionarioPerformanceDto dto = new FuncionarioPerformanceDto();
            dto.setNomeFuncionario(funcionario.getNome());
            dto.setCargo(funcionario.getCargo().toString());
            dto.setTotalPedidosProcessados(totalPedidos);
            dto.setTotalReservasAtendidas(totalReservas);
            dto.setMediaPedidosPorDia(mediaPedidosPorDia);

            performance.add(dto);
        });

        return performance.stream()
                .sorted(Comparator.comparing(FuncionarioPerformanceDto::getTotalPedidosProcessados).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<FuncionarioFaturamentoDto> calcularFuncionariosMaiorFaturamento(LocalDate dataInicio, LocalDate dataFim) {
        List<Funcionario> funcionarios = funcionarioRepository.findByAtivoTrue();
        List<FuncionarioFaturamentoDto> faturamento = new ArrayList<>();

        funcionarios.forEach(funcionario -> {
            // Pedidos do funcionário no período
            List<Pedido> pedidosFuncionario = pedidoRepository.findByFuncionarioAndReservaDataReservaBetween(
                    funcionario, dataInicio, dataFim);

            BigDecimal faturamentoTotal = pedidosFuncionario.stream()
                    .map(Pedido::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            long totalPedidos = pedidosFuncionario.size();
            BigDecimal ticketMedio = totalPedidos > 0 
                    ? faturamentoTotal.divide(BigDecimal.valueOf(totalPedidos), 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            FuncionarioFaturamentoDto dto = new FuncionarioFaturamentoDto();
            dto.setNomeFuncionario(funcionario.getNome());
            dto.setCargo(funcionario.getCargo().toString());
            dto.setFaturamentoTotal(faturamentoTotal);
            dto.setTotalPedidos(totalPedidos);
            dto.setTicketMedio(ticketMedio);

            faturamento.add(dto);
        });

        return faturamento.stream()
                .sorted(Comparator.comparing(FuncionarioFaturamentoDto::getFaturamentoTotal).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
} 