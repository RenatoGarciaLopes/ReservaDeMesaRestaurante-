package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusMesa;
import com.example.demo.enums.StatusReserva;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IFuncionarioRepository;
import com.example.demo.repository.IMesaRepository;
import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.specification.ReservaSpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IMesaRepository mesaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IFuncionarioRepository funcionarioRepository;

    @Autowired
    private HorarioFuncionamentoService horarioFuncionamentoService;

    @Transactional
    public ListarReservaDto salvar(CadastrarReservaDTO Dto) {
        Mesa mesa = mesaRepository.findById(Dto.getMesaId())
                .orElseThrow(() -> new EntityNotFoundException("Mesa não foi encontrado"));

        Cliente cliente = clienteRepository.findById(Dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Clinte não foi encontrado"));

        Funcionario funcionario = new Funcionario();

        if (Dto.getFuncionarioId() != null) {
            funcionario = funcionarioRepository.findById(Dto.getFuncionarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        }

        if (!funcionario.getAtivo())
            throw new IllegalStateException("Funcionário está inativado e não pode realizar essa operação.");

        if (!mesa.getAtivo())
            throw new IllegalStateException("Não é possível utilizar uma mesa inativa");

        if (!cliente.isAtivo())
            throw new IllegalStateException("Cliente está inativado e não pode realizar essa operação.");

        Set<LocalTime> horarios = new HashSet<>(
                horarioFuncionamentoService.gerarHorariosReserva(Dto.getDataReserva().getDayOfWeek()));

        if (!horarios.contains(Dto.getHoraReserva()))
            throw new IllegalStateException("Horário indisponível: tente outro horário");

        if (Dto.getDataReserva().isEqual(LocalDate.now()) &&
                Dto.getHoraReserva().isBefore(LocalTime.now())) {
            throw new IllegalStateException("Horário inválido: não é possível reservar para um horário que já passou");
        }

        boolean e = reservaRepository.findAll().stream()
                .filter(r -> r.getDataReserva().equals(Dto.getDataReserva()))
                .filter(r -> r.getHoraReserva().equals(Dto.getHoraReserva()))
                .filter(r -> r.getMesa().getId().equals(mesa.getId()))
                .anyMatch(r -> r.getStatus() == StatusReserva.CONFIRMADA);

        if (e)
            throw new IllegalStateException("Horário indisponível: tente outra data ou horário");

        if (mesa.getCapacidade() < Dto.getQuantidadePessoas())
            throw new IllegalStateException(
                    "A quantidade de pessoas excede a capacidade da mesa selecionada. Selecione outra mesa ou reduza o número de pessoas");

        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

        Reserva reserva = reservaRepository.save(reservaMapper.toEntity(Dto));
        return reservaMapper.toDto(reserva);
    }

    public ListarReservaDto obterReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não foi encontrada"));

        return reservaMapper.toDto(reserva);
    }

    public Page<ListarReservaDto> listarReservaPorCliente(int pagina, int tamanho, Long id) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataReserva").and(Sort.by("horaReserva")));

        return reservaRepository.findByClienteId(id, pageable).map(reservaMapper::toDto);
    }

    public Page<ListarReservaDto> listarReserva(int pagina, int tamanho,
            LocalDate dataReserva, LocalTime horarioReserva, StatusReserva status, Long mesaId) {
        Specification<Reserva> spec = Specification.where(ReservaSpecification.temData(dataReserva))
                .and(ReservaSpecification.temHorario(horarioReserva))
                .and(ReservaSpecification.temStatus(status))
                .and(ReservaSpecification.temMesa(mesaId));

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataReserva").and(Sort.by("horaReserva")));

        return reservaRepository.findAll(spec, pageable).map(reservaMapper::toDto);
    }

    @Transactional
    public ListarReservaDto confirmarChegadaReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não foi encontrada"));

        if (!reserva.getStatus().equals(StatusReserva.CONFIRMADA)) {
            throw new IllegalStateException("Apenas reservas confirmadas podem ser marcadas como ocupadas");
        }

        Mesa mesa = reserva.getMesa();

        mesa.setStatus(StatusMesa.OCUPADO);
        mesaRepository.save(mesa);

        return reservaMapper.toDto(reserva);
    }

    @Transactional
    public ListarReservaDto concluirReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não foi encontrada"));

        Mesa mesa = reserva.getMesa();

        reserva.setStatus(StatusReserva.CONCLUIDA);
        mesa.setStatus(StatusMesa.LIVRE);

        mesaRepository.save(mesa);

        return reservaMapper.toDto(reservaRepository.save(reserva));
    }

    @Transactional
    public ListarReservaDto cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não foi encontrada"));

        if (!reserva.getStatus().equals(StatusReserva.CONFIRMADA)
                || reserva.getMesa().getStatus() == StatusMesa.OCUPADO) {
            throw new IllegalStateException("Apenas reservas confirmadas podem ser marcadas como canceladas");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);

        Mesa mesa = mesaRepository.findById(reserva.getMesa().getId())
                .orElseThrow(() -> new EntityNotFoundException("Mesa não foi encontrada"));

        mesa.setStatus(StatusMesa.LIVRE);
        mesaRepository.save(mesa);

        return reservaMapper.toDto(reserva);
    }
}