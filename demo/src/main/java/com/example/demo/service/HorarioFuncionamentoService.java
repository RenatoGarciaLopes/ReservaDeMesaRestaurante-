package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HorarioFuncionamentoDto.AtualizarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.CadastrarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.ListarHorarioFuncionamento;
import com.example.demo.entities.HorarioFuncionamento;
import com.example.demo.enums.DiaSemanaPortugues;
import com.example.demo.mapper.HorarioFuncionamentoMapper;
import com.example.demo.repository.IHorarioFuncionamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class HorarioFuncionamentoService {

    @Autowired
    private IHorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Autowired
    private HorarioFuncionamentoMapper horarioFuncionamentoMapper;

    @Transactional
    public ListarHorarioFuncionamento cadastrar(CadastrarHorarioFuncionamento dto) {
        Optional<HorarioFuncionamento> existeDia = horarioFuncionamentoRepository
                .findByDiaFuncionamento(DiaSemanaPortugues.from(dto.getDiaFuncionamento()).toDayOfWeek());

        if (existeDia.isPresent()) {
            throw new IllegalStateException("Este dia da semana já foi cadastrado.");
        }

        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoMapper.toEntity(dto);
        return horarioFuncionamentoMapper.toDto(horarioFuncionamentoRepository.save(horarioFuncionamento));
    }

    public List<ListarHorarioFuncionamento> listarHorarios() {
        return horarioFuncionamentoMapper.toListDto(horarioFuncionamentoRepository.findAll());
    }

    public ListarHorarioFuncionamento obterHorarioPorId(Long id) {
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horário não foi encontrado"));

        return horarioFuncionamentoMapper.toDto(horarioFuncionamento);
    }

    @Transactional
    public ListarHorarioFuncionamento atualizarHorario(Long id, AtualizarHorarioFuncionamento dto) {
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horário não foi encontrado"));

        LocalTime inicio = dto.getHorarioInicio();
        LocalTime fim = dto.getHorarioFim();

        if (inicio != null && fim != null) {
            if (!inicio.isBefore(fim)) {
                throw new IllegalArgumentException("O horário de início deve ser antes do horário de fim.");
            }
            horarioFuncionamento.setHorarioInicio(inicio);
            horarioFuncionamento.setHorarioFim(fim);
        } else if (inicio != null) {
            if (!inicio.isBefore(horarioFuncionamento.getHorarioFim())) {
                throw new IllegalArgumentException("O horário de início deve ser antes do horário de fim atual.");
            }
            horarioFuncionamento.setHorarioInicio(inicio);
        } else if (fim != null) {
            if (!horarioFuncionamento.getHorarioInicio().isBefore(fim)) {
                throw new IllegalArgumentException("O horário de fim deve ser após o horário de início atual.");
            }
            horarioFuncionamento.setHorarioFim(fim);
        }

        return horarioFuncionamentoMapper.toDto(horarioFuncionamentoRepository.save(horarioFuncionamento));
    }

    public void removerHorario(Long id) {
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Horário não foi encontrado"));

        horarioFuncionamentoRepository.delete(horarioFuncionamento);
    }

    public List<LocalTime> gerarHorariosReserva(DayOfWeek dia) {
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoRepository.findByDiaFuncionamento(dia)
                .orElseThrow(() -> new EntityNotFoundException(
                        "O restaurante não está disponível para reservas nesse dia. Por favor, escolha outro dia."));

        List<LocalTime> horarios = new ArrayList<>();

        LocalTime inicio = horarioFuncionamento.getHorarioInicio();
        LocalTime fim = horarioFuncionamento.getHorarioFim();

        for (LocalTime hora = inicio; hora.isBefore(fim); hora = hora.plusMinutes(30)) {
            horarios.add(hora);
        }

        return horarios;
    }
}
