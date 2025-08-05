package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MesaDto.AtualizarMesaDto;
import com.example.demo.dto.MesaDto.AtualizarStatusMesaDto;
import com.example.demo.dto.MesaDto.CadastrarMesaDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.entities.Mesa;
import com.example.demo.enums.StatusMesa;
import com.example.demo.mapper.MesaMapper;
import com.example.demo.repository.IMesaRepository;
import com.example.demo.repository.specification.MesaSpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MesaService {

    @Autowired
    private IMesaRepository mesaRepository;

    @Autowired
    private MesaMapper mesaMapper;

    @Transactional
    public ListarMesaDto salvar(CadastrarMesaDto mesaDto) {
        Mesa mesa = mesaMapper.toEntity(mesaDto);
        return mesaMapper.toDto(mesaRepository.save(mesa));
    }

    public Page<ListarMesaDto> listarMesas(int pagina, int tamanho,
            StatusMesa status, Integer capacidade, Boolean ativo) {
        Specification<Mesa> spec = Specification.where(MesaSpecification.temStatus(status))
                .and(MesaSpecification.temCapacidade(capacidade))
                .and(MesaSpecification.isAtivo(ativo));

        Pageable pageable;
        if (tamanho == 0) {
            // Se tamanho for 0, lista todos os itens sem paginação
            pageable = Pageable.unpaged();
        } else {
            // Caso contrário, respeita o tamanho indicado
            pageable = PageRequest.of(pagina, tamanho, Sort.by("numero"));
        }

        return mesaRepository.findAll(spec, pageable).map(mesaMapper::toDto);
    }

    public Page<ListarMesaDto> listarMesasDisponiveis(int pagina, int tamanho) {
        Specification<Mesa> spec = Specification.where(MesaSpecification.temStatus(StatusMesa.LIVRE));

        Pageable pageable;
        if (tamanho == 0) {
            // Se tamanho for 0, lista todos os itens sem paginação
            pageable = Pageable.unpaged();
        } else {
            // Caso contrário, respeita o tamanho indicado
            pageable = PageRequest.of(pagina, tamanho, Sort.by("numero"));
        }

        return mesaRepository.findAll(spec, pageable).map(mesaMapper::toDto);
    }

    public ListarMesaDto obterMesaPeloId(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        return mesaMapper.toDto(mesa);
    }

    @Transactional
    public ListarMesaDto atualizarMesa(Long id, AtualizarMesaDto mesaDto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        if (mesaDto.getNumero() != null) {
            mesa.setNumero(mesaDto.getNumero());
        }
        if (mesaDto.getCapacidade() != null) {
            mesa.setCapacidade(mesaDto.getCapacidade());
        }

        return mesaMapper.toDto(mesaRepository.save(mesa));
    }

    @Transactional
    public ListarMesaDto atualizarStatusMesa(Long id, AtualizarStatusMesaDto mesaDto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        mesa.setStatus(mesaDto.getStatus());

        return mesaMapper.toDto(mesaRepository.save(mesa));
    }

    @Transactional
    public void inativarMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        if (mesa.getStatus() != StatusMesa.LIVRE) {
            throw new RuntimeException("Não é possível inativar uma mesa ocupada ou reservada");
        }

        mesa.setAtivo(false);
        mesaRepository.save(mesa);
    }

    @Transactional
    public void reativarMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        if (mesa.getAtivo() == true) {
            throw new IllegalStateException("Mesa já está ativa");
        }

        mesa.setAtivo(true);
        mesaRepository.save(mesa);
    }
}
