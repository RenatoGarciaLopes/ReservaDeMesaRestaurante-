package com.example.demo.mapper.Utils;

import java.time.DayOfWeek;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.example.demo.enums.DiaSemanaPortugues;

@Component
public class HorarioFuncionamentoMapperHelper {

    @Named("converterDiaStringParaDayOfWeek")
    public DayOfWeek converterDiaStringParaDayOfWeek(String dia) {
        return DiaSemanaPortugues.from(dia).toDayOfWeek();
    }
}
