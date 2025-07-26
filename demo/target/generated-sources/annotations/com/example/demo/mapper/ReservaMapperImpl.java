package com.example.demo.mapper;

import com.example.demo.dto.CadastrarReservaDTO;
import com.example.demo.dto.ListarReservaDto;
import com.example.demo.entities.Cliente;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Mesa;
import com.example.demo.entities.Reserva;
import com.example.demo.enums.Cargo;
import com.example.demo.mapper.Utils.ReservaMapperHelper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-26T13:45:08-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservaMapperImpl implements ReservaMapper {

    @Autowired
    private ReservaMapperHelper reservaMapperHelper;

    @Override
    public Reserva toEntity(CadastrarReservaDTO reservaDTO) {
        if ( reservaDTO == null ) {
            return null;
        }

        Reserva reserva = new Reserva();

        reserva.setCliente( reservaMapperHelper.buscarCliente( reservaDTO.getClienteId() ) );
        reserva.setMesa( reservaMapperHelper.buscarMesa( reservaDTO.getMesaId() ) );
        reserva.setFuncionario( reservaMapperHelper.buscarFuncionario( reservaDTO.getFuncionarioId() ) );
        reserva.setDataReserva( reservaDTO.getDataReserva() );
        reserva.setHoraReserva( reservaDTO.getHoraReserva() );
        reserva.setQuantidadePessoas( reservaDTO.getQuantidadePessoas() );

        return reserva;
    }

    @Override
    public ListarReservaDto toDto(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }

        ListarReservaDto listarReservaDto = new ListarReservaDto();

        listarReservaDto.setNomeCliente( reservaClienteNome( reserva ) );
        listarReservaDto.setCpf( reservaClienteCpf( reserva ) );
        listarReservaDto.setTelefone( reservaClienteTelefone( reserva ) );
        listarReservaDto.setNumeroMesa( reservaMesaNumero( reserva ) );
        listarReservaDto.setNomeFuncionario( reservaFuncionarioNome( reserva ) );
        listarReservaDto.setCargo( reservaFuncionarioCargo( reserva ) );
        listarReservaDto.setDataReserva( reserva.getDataReserva() );
        listarReservaDto.setHoraReserva( reserva.getHoraReserva() );
        listarReservaDto.setId( reserva.getId() );
        listarReservaDto.setQuantidadePessoas( reserva.getQuantidadePessoas() );
        listarReservaDto.setStatus( reserva.getStatus() );

        return listarReservaDto;
    }

    @Override
    public List<ListarReservaDto> toListDtos(List<Reserva> reservas) {
        if ( reservas == null ) {
            return null;
        }

        List<ListarReservaDto> list = new ArrayList<ListarReservaDto>( reservas.size() );
        for ( Reserva reserva : reservas ) {
            list.add( toDto( reserva ) );
        }

        return list;
    }

    private String reservaClienteNome(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Cliente cliente = reserva.getCliente();
        if ( cliente == null ) {
            return null;
        }
        String nome = cliente.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String reservaClienteCpf(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Cliente cliente = reserva.getCliente();
        if ( cliente == null ) {
            return null;
        }
        String cpf = cliente.getCpf();
        if ( cpf == null ) {
            return null;
        }
        return cpf;
    }

    private String reservaClienteTelefone(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Cliente cliente = reserva.getCliente();
        if ( cliente == null ) {
            return null;
        }
        String telefone = cliente.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone;
    }

    private Integer reservaMesaNumero(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Mesa mesa = reserva.getMesa();
        if ( mesa == null ) {
            return null;
        }
        Integer numero = mesa.getNumero();
        if ( numero == null ) {
            return null;
        }
        return numero;
    }

    private String reservaFuncionarioNome(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Funcionario funcionario = reserva.getFuncionario();
        if ( funcionario == null ) {
            return null;
        }
        String nome = funcionario.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private Cargo reservaFuncionarioCargo(Reserva reserva) {
        if ( reserva == null ) {
            return null;
        }
        Funcionario funcionario = reserva.getFuncionario();
        if ( funcionario == null ) {
            return null;
        }
        Cargo cargo = funcionario.getCargo();
        if ( cargo == null ) {
            return null;
        }
        return cargo;
    }
}
