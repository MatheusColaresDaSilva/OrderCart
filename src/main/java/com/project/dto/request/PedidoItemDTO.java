package com.project.dto.request;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PedidoItemDTO {

    @NotNull(message = "Item id may not be null")
    private Long itemId;

    private Integer qtd;
}
