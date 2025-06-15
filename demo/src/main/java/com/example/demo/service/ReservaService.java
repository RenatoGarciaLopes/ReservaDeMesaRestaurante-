package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AtualizarStatusReservaDto;
import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.StatusMesa;
import com.example.demo.enums.StatusReserva;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.IMesaRepository;
import com.example.demo.repository.IReservaRepository;

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
    private HorarioFuncionamentoService horarioFuncionamentoService;

    @Transactional
    public ListarReservaDto salvar(CadastrarReservaDTO Dto) {
        Mesa mesa = mesaRepository.findById(Dto.getMesaId())
                .orElseThrow(() -> new EntityNotFoundException("Mesa não existe"));

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
                .filter(r -> r.getMesa().getNumero().equals(mesa.getNumero()))
                .anyMatch(r -> r.getStatus() == StatusReserva.CONFIRMADA);

        if (e)
            throw new IllegalStateException("Horário indisponível: tente outra data ou horário");

        if (mesa.getCapacidade() < Dto.getQuantidadePessoas())
            throw new IllegalStateException(
                    "A quantidade de pessoas excede a capacidade da mesa selecionada. Selecione outra mesa ou reduza o número de pessoas.");

        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

        Reserva reserva = reservaRepository.save(reservaMapper.toEntity(Dto));
        return reservaMapper.toDto(reserva);
    }

    public ListarReservaDto obterReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        return reservaMapper.toDto(reserva);
    }

    public List<ListarReservaDto> listarReservaPorCliente(Long id) {
        return reservaRepository.findAll().stream()
                .filter(r -> r.getCliente().getId().equals(id))
                .map(reservaMapper::toDto)
                .toList();
    }

    @Transactional
    public ListarReservaDto atualizarStatusReserva(Long id, AtualizarStatusReservaDto reservaDto) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        reserva.setStatus(reservaDto.getStatus());

        if (reserva.getStatus().equals(StatusReserva.CANCELADA)
                || reserva.getStatus().equals(StatusReserva.CONCLUIDA)) {
            Mesa mesa = mesaRepository.findById(reserva.getMesa().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Mesa não existe"));

            mesa.setStatus(StatusMesa.LIVRE);
            mesaRepository.save(mesa);
        }

        return reservaMapper.toDto(reservaRepository.save(reserva));
    }

    public List<ListarReservaDto> listarReserva() {
        return reservaMapper.toListDtos(reservaRepository.findAll());
    }

    @Transactional
    public void removerReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        reserva.setStatus(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);

        Mesa mesa = mesaRepository.findById(reserva.getMesa().getId())
                .orElseThrow(() -> new EntityNotFoundException("Mesa não existe"));

        mesa.setStatus(StatusMesa.LIVRE);
        mesaRepository.save(mesa);
    }
}