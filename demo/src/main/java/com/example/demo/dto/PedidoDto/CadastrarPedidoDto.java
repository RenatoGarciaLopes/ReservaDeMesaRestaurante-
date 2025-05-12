package com.example.demo.dto.PedidoDto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class CadastrarPedidoDto {

    @NotNull(message = "ID da reserva é obrigatório")
    private Long reserva_id;

    @NotNull(message = "Quantidade e ID do item do cardápio é obrigatório")
    private List<ItemQuantidadeDto> pedidos;

}
