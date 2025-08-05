package com.example.demo.mapper;

import com.example.demo.dto.ClienteDto.CadastroClienteDto;
import com.example.demo.dto.ClienteDto.ListarClienteDto;
import com.example.demo.entities.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-04T19:21:44-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(CadastroClienteDto ClienteDto) {
        if ( ClienteDto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setCpf( ClienteDto.getCpf() );
        cliente.setEmail( ClienteDto.getEmail() );
        cliente.setNome( ClienteDto.getNome() );
        cliente.setObservacoes( ClienteDto.getObservacoes() );
        cliente.setTelefone( ClienteDto.getTelefone() );

        return cliente;
    }

    @Override
    public ListarClienteDto toDto(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ListarClienteDto listarClienteDto = new ListarClienteDto();

        listarClienteDto.setCpf( cliente.getCpf() );
        listarClienteDto.setDataCadastro( cliente.getDataCadastro() );
        listarClienteDto.setEmail( cliente.getEmail() );
        listarClienteDto.setId( cliente.getId() );
        listarClienteDto.setNome( cliente.getNome() );
        listarClienteDto.setObservacoes( cliente.getObservacoes() );
        listarClienteDto.setTelefone( cliente.getTelefone() );

        return listarClienteDto;
    }

    @Override
    public List<ListarClienteDto> toDtoLIst(List<Cliente> clientes) {
        if ( clientes == null ) {
            return null;
        }

        List<ListarClienteDto> list = new ArrayList<ListarClienteDto>( clientes.size() );
        for ( Cliente cliente : clientes ) {
            list.add( toDto( cliente ) );
        }

        return list;
    }
}
