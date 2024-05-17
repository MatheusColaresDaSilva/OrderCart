package com.project.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PedidoResponseDTO {

    private Long idPedido;
    private Long idMesa;
    private List<PedidoResponseDTO> pedidoItens;
    private String situacaoPedido;
}
