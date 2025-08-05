package com.example.demo.mapper;

import com.example.demo.dto.FuncionarioDto.CadastrarFuncionarioDto;
import com.example.demo.dto.FuncionarioDto.ListarFuncionarioDto;
import com.example.demo.entities.Funcionario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-04T19:32:30-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class FuncionarioMapperImpl implements FuncionarioMapper {

    @Override
    public Funcionario toEntity(CadastrarFuncionarioDto dto) {
        if ( dto == null ) {
            return null;
        }

        Funcionario funcionario = new Funcionario();

        funcionario.setCargo( dto.getCargo() );
        funcionario.setCpf( dto.getCpf() );
        funcionario.setDataContratacao( dto.getDataContratacao() );
        funcionario.setEmail( dto.getEmail() );
        funcionario.setNome( dto.getNome() );
        funcionario.setSalario( dto.getSalario() );
        funcionario.setSenha( dto.getSenha() );
        funcionario.setTelefone( dto.getTelefone() );

        return funcionario;
    }

    @Override
    public ListarFuncionarioDto toDto(Funcionario entity) {
        if ( entity == null ) {
            return null;
        }

        ListarFuncionarioDto listarFuncionarioDto = new ListarFuncionarioDto();

        listarFuncionarioDto.setCargo( entity.getCargo() );
        listarFuncionarioDto.setCpf( entity.getCpf() );
        listarFuncionarioDto.setDataContratacao( entity.getDataContratacao() );
        listarFuncionarioDto.setEmail( entity.getEmail() );
        listarFuncionarioDto.setFotoPerfil( entity.getFotoPerfil() );
        listarFuncionarioDto.setId( entity.getId() );
        listarFuncionarioDto.setNome( entity.getNome() );
        listarFuncionarioDto.setSalario( entity.getSalario() );
        listarFuncionarioDto.setTelefone( entity.getTelefone() );

        return listarFuncionarioDto;
    }

    @Override
    public List<ListarFuncionarioDto> toDtoList(List<Funcionario> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ListarFuncionarioDto> list = new ArrayList<ListarFuncionarioDto>( entities.size() );
        for ( Funcionario funcionario : entities ) {
            list.add( toDto( funcionario ) );
        }

        return list;
    }
}
