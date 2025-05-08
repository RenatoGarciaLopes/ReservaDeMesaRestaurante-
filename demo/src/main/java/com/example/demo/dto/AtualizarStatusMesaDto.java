package com.example.demo.dto;

import com.example.demo.enums.StatusMesa;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class AtualizarStatusMesaDto {

    @NotNull(message = "O estado da mesa é obrigatório")
    private StatusMesa status;
}
