package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Reserva;
import com.example.demo.mapper.Utils.ReservaMapperHelper;

@Mapper(componentModel = "spring", uses = ReservaMapperHelper.class)
public interface ReservaMapper {

    // pegar as informacoes do Cadastrar reserva DTO e transforma em um objeto
    // Reserva
    @Mapping(source = "clienteId", target = "cliente", qualifiedByName = "BuscarCliente")
    @Mapping(source = "mesaId", target = "mesa", qualifiedByName = "BuscarMesa")
    @Mapping(source = "funcionarioId", target = "funcionario", qualifiedByName = "BuscarFuncionario")
    Reserva toEntity(CadastrarReservaDTO reservaDTO);

    // pegar o objeto Reserva e transforma em DTO
    @Mapping(target = "nomeCliente", source = "cliente.nome")
    @Mapping(target = "cpf", source = "cliente.cpf")
    @Mapping(target = "telefone", source = "cliente.telefone")
    @Mapping(target = "numeroMesa", source = "mesa.numero")
    @Mapping(target = "nomeFuncionario", source = "funcionario.nome")
    @Mapping(target = "cargo", source = "funcionario.cargo")
    ListarReservaDto toDto(Reserva reserva);

    // Pega a lista de reservas e transforma na Lista de DTO
    List<ListarReservaDto> toListDtos(List<Reserva> reservas);
}
