package com.project.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ItemResponseDTO {
    private Long id;
    private String descricao;
}
