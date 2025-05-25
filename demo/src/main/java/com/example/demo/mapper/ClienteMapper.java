package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.demo.dto.ClienteDto.CadastroClienteDto;
import com.example.demo.dto.ClienteDto.ListarClienteDto;
import com.example.demo.entities.Cliente;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ClienteMapper {

    Cliente toEntity(CadastroClienteDto ClienteDto);

    ListarClienteDto toDto(Cliente cliente);

    List<ListarClienteDto> toDtoLIst (List<Cliente> clientes);
}

