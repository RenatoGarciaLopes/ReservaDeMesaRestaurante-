package com.example.demo.validation;

import java.time.LocalTime;

import com.example.demo.dto.HorarioFuncionamentoDto.CadastrarHorarioFuncionamento;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntervaloHorarioValidator
        implements ConstraintValidator<IntervaloHorarioValido, CadastrarHorarioFuncionamento> {

    @Override
    public boolean isValid(CadastrarHorarioFuncionamento value, ConstraintValidatorContext context) {
        LocalTime inicio = value.getHorarioInicio();
        LocalTime fim = value.getHorarioFim();

        return fim.isAfter(inicio);
    }
}
