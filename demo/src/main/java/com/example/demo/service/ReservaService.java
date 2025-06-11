package com.example.demo.service;

import java.util.List;

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

    @Transactional
    public ListarReservaDto salvar(CadastrarReservaDTO DTO) {
        Mesa mesa = mesaRepository.findById(DTO.getMesaId())
                .orElseThrow(() -> new EntityNotFoundException("Mesa nao existe"));

        if (reservaRepository.existsByMesa_Id(DTO.getMesaId()) && mesa.getStatus().equals(StatusMesa.RESERVADA)) {
            boolean existe = reservaRepository.findAll().stream()
                    .filter(r -> r.getDataReserva().equals(DTO.getDataReserva()))
                    .anyMatch(r -> r.getHoraReserva().equals(DTO.getHoraReserva()));

            if (existe) {
                throw new IllegalStateException("Escolha outro horário ou data");
            }
        }

        mesa.setStatus(StatusMesa.RESERVADA);
        mesaRepository.save(mesa);

        Reserva reserva = reservaRepository.save(reservaMapper.toEntity(DTO));
        return reservaMapper.toDto(reserva);

    }

    public ListarReservaDto obterReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva nao encontrada"));

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
                    .orElseThrow(() -> new EntityNotFoundException("Mesa nao existe"));

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
                .orElseThrow(() -> new EntityNotFoundException("Mesa nao existe"));

        mesa.setStatus(StatusMesa.LIVRE);
        mesaRepository.save(mesa);
    }
}