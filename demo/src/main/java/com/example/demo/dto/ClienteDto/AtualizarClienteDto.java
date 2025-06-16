package com.example.demo.dto.ClienteDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class AtualizarClienteDto {

    private String nome;

    @Email
    private String email;

    @Schema(example = "(XX)XXXXXXXX ou (XX)9XXXX-XXXX")
    @Pattern(regexp = "\\(\\d{2}\\)?:\\d{4,5})\\d{4}", message = "Formato inválido. Use (XX)XXXX-XXXX ou (XX)9XXXX-XXXX")
    private String telefone;

    private String observacoes;
}
