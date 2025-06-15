package com.example.demo.mapper;

import com.example.demo.dto.HorarioFuncionamentoDto.CadastrarHorarioFuncionamento;
import com.example.demo.dto.HorarioFuncionamentoDto.ListarHorarioFuncionamento;
import com.example.demo.entities.HorarioFuncionamento;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T19:32:39-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class HorarioFuncionamentoMapperImpl implements HorarioFuncionamentoMapper {

    @Override
    public HorarioFuncionamento toEntity(CadastrarHorarioFuncionamento dto) {
        if ( dto == null ) {
            return null;
        }

        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento();

        horarioFuncionamento.setDiaFuncionamento( dto.getDiaFuncionamento() );
        horarioFuncionamento.setHorarioFim( dto.getHorarioFim() );
        horarioFuncionamento.setHorarioInicio( dto.getHorarioInicio() );

        return horarioFuncionamento;
    }

    @Override
    public ListarHorarioFuncionamento toDto(HorarioFuncionamento entity) {
        if ( entity == null ) {
            return null;
        }

        ListarHorarioFuncionamento listarHorarioFuncionamento = new ListarHorarioFuncionamento();

        listarHorarioFuncionamento.setDiaFuncionamento( entity.getDiaFuncionamento() );
        listarHorarioFuncionamento.setHorarioFim( entity.getHorarioFim() );
        listarHorarioFuncionamento.setHorarioInicio( entity.getHorarioInicio() );

        return listarHorarioFuncionamento;
    }

    @Override
    public List<ListarHorarioFuncionamento> toListDto(List<HorarioFuncionamento> list) {
        if ( list == null ) {
            return null;
        }

        List<ListarHorarioFuncionamento> list1 = new ArrayList<ListarHorarioFuncionamento>( list.size() );
        for ( HorarioFuncionamento horarioFuncionamento : list ) {
            list1.add( toDto( horarioFuncionamento ) );
        }

        return list1;
    }
}
