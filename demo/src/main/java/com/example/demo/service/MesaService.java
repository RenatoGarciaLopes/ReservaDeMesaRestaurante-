package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MesaDto.AtualizarMesaDto;
import com.example.demo.dto.MesaDto.AtualizarStatusMesaDto;
import com.example.demo.dto.MesaDto.CadastrarMesaDto;
import com.example.demo.dto.MesaDto.ListarMesaDto;
import com.example.demo.entities.Mesa;
import com.example.demo.enums.StatusMesa;
import com.example.demo.mapper.MesaMapper;
import com.example.demo.repository.IMesaRepository;
import com.example.demo.repository.IReservaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MesaService {

    @Autowired
    private IMesaRepository mesaRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private MesaMapper mesaMapper;

    public ListarMesaDto salvar(CadastrarMesaDto mesaDto) {
        Mesa mesa = mesaMapper.toEntity(mesaDto);
        return mesaMapper.toDto(mesaRepository.save(mesa));
    }

    public List<ListarMesaDto> listarMesas() {
        return mesaMapper.toDtoLIst(mesaRepository.findAll());
    }

    public List<ListarMesaDto> listarMesasDisponiveis() {
        List<Mesa> mesasDisponiveis = mesaRepository.findAll().stream()
                .filter(m -> m.getStatus().equals(StatusMesa.LIVRE))
                .toList();

        return mesaMapper.toDtoLIst(mesasDisponiveis);
    }

    public ListarMesaDto obterMesaPeloId(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        return mesaMapper.toDto(mesa);
    }

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

    public ListarMesaDto atualizarStatusMesa(Long id, AtualizarStatusMesaDto mesaDto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));

        mesa.setStatus(mesaDto.getStatus());

        return mesaMapper.toDto(mesaRepository.save(mesa));
    }

    public void removerMesa(Long id) {
        if (!mesaRepository.existsById(id)) {
            throw new EntityNotFoundException("Mesa não encontrada");
        }

        boolean existe = reservaRepository.existsByMesa_Id(id);

        if (existe) {
            throw new IllegalStateException("A mesa está associada a uma reserva e não pode ser removida.");
        }

        mesaRepository.deleteById(id);
    }
}
