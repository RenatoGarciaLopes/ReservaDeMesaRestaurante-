package com.example.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = IntervaloHorarioValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IntervaloHorarioValido {

    String message() default "Horário final deve ser depois do horário inicial";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
