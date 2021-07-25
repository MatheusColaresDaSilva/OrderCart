package com.project.controller;

import com.project.dto.response.ErroResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class ControllerBase {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDTO<Object>> handleException(BusinessException ex) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.comErro(erroResponseDTO));
    }
}
