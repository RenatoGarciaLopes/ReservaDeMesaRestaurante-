package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    //pegar as informacoes do Cadastrar reserva  DTO e transforma em um objeto Reserva
    Reserva toEntity(CadastrarReservaDTO reservaDTO);
    
    //pegar o objeto Reserva e transforma em DTO
    ListarReservaDto toDto(Reserva reserva);

    //Pega a lista de reservas e transforma na Lista de DTO
    List<ListarReservaDto> toListDtos(List<Reserva> reservas);

}
