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

    public List<ListarClienteDto> listarClientePorStatus(Boolean status) {
        return clienteMapper.toDtoLIst(clienteRepository.findByAtivo(status));
    }

    public ListarClienteDto obterClientePeloId(long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

        return clienteMapper.toDto(cliente);
    }

    public ListarClienteDto obterClientePeloCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("\\D", "");
        Cliente cliente = clienteRepository.findByCpf(cpfLimpo)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

        return clienteMapper.toDto(cliente);
    }

    public ListarClienteDto atualizarCliente(Long id, AtualizarClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

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

    public void inativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    public void reativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));

        if (cliente.getStatus() == true) {
            throw new IllegalStateException("Cliente já está ativo");
        }

        cliente.setAtivo(true);
        clienteRepository.save(cliente);
    }
}