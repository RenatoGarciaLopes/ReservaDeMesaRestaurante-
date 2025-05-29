package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Reserva;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.IReservaRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservaService {

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private IReservaRepository reservaRepository;

    @Transactional
    public ListarReservaDto salvar(CadastrarReservaDTO DTO) {
        boolean existe = reservaRepository.findAll().stream()
                .filter(r -> r.getDataReserva().equals(DTO.getHoraReserva()))
                .anyMatch(r -> r.getHoraReserva().equals(DTO.getHoraReserva()));     

        if (existe) {
            throw new IllegalStateException("Escolha outro horário ou data");
        }

        Reserva reserva = reservaRepository.save(reservaMapper.toEntity(DTO));  
        return reservaMapper.toDto(reserva);
    }
}
