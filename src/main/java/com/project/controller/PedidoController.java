package com.project.controller;

import com.project.dto.request.PedidoRequestDTO;
import com.project.dto.response.PedidoResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController extends ControllerBase{

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PedidoResponseDTO>> criaPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        final PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(pedidoResponseDTO));
    }
}
