package com.project.service;

import com.project.dto.request.PedidoRequestDTO;
import com.project.dto.response.PedidoResponseDTO;
import com.project.entity.Mesa;
import com.project.entity.Pedido;
import com.project.enumerator.SituacaoPedido;
import com.project.exception.MesaNaoEncontradaException;
import com.project.repository.MesaRepository;
import com.project.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    private MesaRepository mesaRepository;

    private PedidoRepository pedidoRepository;
    public PedidoService(MesaRepository mesaRepository, PedidoRepository pedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        final Pedido pedido = Pedido.builder()
                .mesa(retornaMesaPedio(pedidoRequestDTO))
                .situacaoPedido(SituacaoPedido.ABERTO.getCodigo()).build();

        return entityToPedidoResponseDTO(pedidoRepository.save(pedido));
    }

    private PedidoResponseDTO entityToPedidoResponseDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .idPedido(pedido.getId())
                .idMesa(pedido.getMesa().getId())
                .situacaoPedido(pedido.getSituacaoPedido()).build();
    }

    private Mesa retornaMesaPedio(PedidoRequestDTO pedidoRequestDTO) {
        return mesaRepository.findById(pedidoRequestDTO.getMesaId()).orElseThrow(() -> new MesaNaoEncontradaException());
    }
}
