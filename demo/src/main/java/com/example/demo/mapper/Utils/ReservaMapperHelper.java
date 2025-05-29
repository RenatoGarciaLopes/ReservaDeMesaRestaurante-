package com.example.demo.mapper.Utils;

import javax.swing.text.html.parser.Entity;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Mesa;
import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IMesaRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ReservaMapperHelper {

    @Autowired
    private IMesaRepository mesaRepository;
    
    @Autowired
    private IClienteRepository clienteRepository;

    @Named("BuscarMesa")
    public Mesa buscarMesa(Long mesaId){
        Mesa mesa = mesaRepository.findById(mesaId)
        .orElseThrow(()-> new EntityNotFoundException("Mesa não encontrada"));

        return mesa;
    }
    
    @Named("BuscarCliente")
    public Cliente buscarCliente(Long clienteId){
        Cliente cliente = clienteRepository.findById(clienteId)
        .orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado"));

        return cliente;
    }
}
