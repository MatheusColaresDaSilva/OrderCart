package com.project.dto.response;

import com.project.dto.request.CardapioItemRequestDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardapioResponseDTO {

    private Long id;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<CardapioItemResponseDTO> itens;
    private String situcaoCardapio;
}
