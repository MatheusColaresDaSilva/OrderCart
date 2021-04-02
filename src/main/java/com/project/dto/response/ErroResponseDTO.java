package com.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErroResponseDTO {

    private String campo;
    private String mensagem;

    public ErroResponseDTO (String mensagem) {
        this.mensagem = mensagem;
    }

}
