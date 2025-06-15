package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.dto.ClienteDto.AtualizarClienteDto;
import com.example.demo.dto.ClienteDto.CadastroClienteDto;
import com.example.demo.dto.ClienteDto.ListarClienteDto;
import com.example.demo.entities.Cliente;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IReservaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Validated
@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Transactional
    public ListarClienteDto salvar(CadastroClienteDto clienteDto) {
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    public List<ListarClienteDto> listarCliente() {
        return clienteMapper.toDtoLIst(clienteRepository.findAll());
    }

    public ListarClienteDto obterClientePeloId(long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        return clienteMapper.toDto(cliente);
    }

    public ListarClienteDto atualizarCliente(Long id, AtualizarClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        if (clienteDto.getNome() != null) {
            cliente.setNome(clienteDto.getNome());
        }

        if (clienteDto.getEmail() != null) {
            cliente.setEmail(clienteDto.getEmail());
        }

        if (clienteDto.getTelefone() != null) {
            cliente.setTelefone(clienteDto.getTelefone());
        }

        if (clienteDto.getObservacoes() != null) {
            cliente.setObservacoes(clienteDto.getObservacoes());
        }

        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    public void removerCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }

        boolean existe = reservaRepository.existsByCliente_Id(id);

        if (existe) {
            throw new IllegalStateException("O cliente tem uma reserva no restaurante, ele não pode ser removido");

        }

        clienteRepository.deleteById(id);
    }
}