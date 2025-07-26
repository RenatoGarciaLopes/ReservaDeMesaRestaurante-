package com.example.demo.dto.FuncionarioDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterarSenhaDto {

    @NotBlank(message = "A senha atual é obrigatória")
    @Schema(example = "senha123", description = "Senha atual do funcionário")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres")
    @Schema(example = "novaSenha123", description = "Nova senha do funcionário")
    private String novaSenha;

    @NotBlank(message = "A confirmação da nova senha é obrigatória")
    @Schema(example = "novaSenha123", description = "Confirmação da nova senha")
    private String confirmarNovaSenha;
} 