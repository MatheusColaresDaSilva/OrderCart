package com.project.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardapioItemResponseDTO {

    private Long idCardapio;
    private Long idItem;
    private BigDecimal valor;
}
