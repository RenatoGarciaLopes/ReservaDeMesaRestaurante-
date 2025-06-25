package com.example.demo.mapper.Utils;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Mesa;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IFuncionarioRepository;
import com.example.demo.repository.IMesaRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ReservaMapperHelper {

    @Autowired
    private IMesaRepository mesaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IFuncionarioRepository funcionarioRepository;

    @Named("BuscarMesa")
    public Mesa buscarMesa(Long mesaId) {
        return mesaRepository.findById(mesaId)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada"));
    }

    @Named("BuscarCliente")
    public Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    @Named("BuscarFuncionario")
    public Funcionario buscarFuncionario(Long id) {
        if (id == null) return null;

        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
    }
}
