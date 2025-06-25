package com.example.demo.dto.FuncionarioDto;

import java.math.BigDecimal;

import com.example.demo.enums.Cargo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarFuncionarioDto {

    private String nome;

    @Schema(example = "(XX)XXXXXXXX ou (XX)9XXXXXXXX")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}\\d{4}", message = "Formato inválido. Use (XX)XXXXXXXX ou (XX)9XXXXXXXX")
    private String telefone;

    @Email
    private String email;

    private Cargo cargo;

    private BigDecimal salario;

}
