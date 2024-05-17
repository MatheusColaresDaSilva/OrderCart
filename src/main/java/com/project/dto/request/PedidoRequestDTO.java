package com.project.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PedidoRequestDTO {

    private Long mesaId;

    private List<PedidoItemDTO> pedidoItens;
}
