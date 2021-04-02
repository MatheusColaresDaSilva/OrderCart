package com.project.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardapioItemRequestDTO {
    private Long item;
    private BigDecimal valor;
}
