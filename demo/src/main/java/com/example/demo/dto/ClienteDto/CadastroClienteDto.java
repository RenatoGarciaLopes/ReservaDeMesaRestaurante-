package com.example.demo.dto.ClienteDto;

import com.example.demo.validation.CpfValido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CadastroClienteDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @CpfValido
    private String cpf;

    @Email
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Schema(example = "(XX)XXXXXXXX ou (XX)9XXXXXXXX")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}\\d{4}", message = "Formato inválido. Use (XX)XXXXXXXX ou (XX)9XXXXXXXX")
    private String telefone;

    private String observacoes;
}