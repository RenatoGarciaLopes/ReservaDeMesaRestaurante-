package com.example.demo.dto.FuncionarioDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.enums.Cargo;
import com.example.demo.validation.CpfValido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CadastrarFuncionarioDto {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @CpfValido
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório")
    @Schema(example = "XXXXXXXXXX ou XX9XXXXXXXX")
    @Pattern(regexp = "\\d{2}\\d{4,5}\\d{4}", message = "Formato inválido. Use XXXXXXXXXX ou XX9XXXXXXXX")
    private String telefone;

    @Email
    private String email;

    @NotNull(message = "Cargo é obrigatório")
    private Cargo cargo;

    @NotNull(message = "Salário é obrigatório")
    private BigDecimal salario;

    @NotNull(message = "Data de contratação é obrigatório")
    private LocalDate dataContratacao;
}
