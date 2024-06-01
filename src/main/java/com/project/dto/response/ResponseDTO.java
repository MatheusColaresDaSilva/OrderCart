package com.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseDTO<T> {

    private T content;
    private List<ErroResponseDTO> erros;

    public ResponseDTO(T content) {
        this.content = content;
    }

    public static ResponseDTO<Object> comErros(List<ErroResponseDTO> erros){
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();

        responseDTO.setErros(erros);

        return responseDTO;
    }

    public static ResponseDTO<Object> comErro(ErroResponseDTO erro){
        return comErros(Arrays.asList(erro));
    }
}
