package com.project.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardapioResquestDTO {

    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<CardapioItemRequestDTO> itens = new ArrayList<>();
    private String situcaoCardapio;
}
