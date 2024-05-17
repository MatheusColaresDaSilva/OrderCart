package com.project.service;

import com.project.dto.request.PedidoItemDTO;
import com.project.dto.request.PedidoRequestDTO;
import com.project.dto.response.CardapioItemResponseDTO;
import com.project.dto.response.PedidoResponseDTO;
import com.project.entity.Item;
import com.project.entity.Mesa;
import com.project.entity.Pedido;
import com.project.entity.PedidoItem;
import com.project.enumerator.SituacaoPedido;
import com.project.exception.BusinessException;
import com.project.exception.MesaNaoEncontradaException;
import com.project.repository.ItemRepository;
import com.project.repository.MesaRepository;
import com.project.repository.PedidoItemRepository;
import com.project.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private MesaRepository mesaRepository;

    private PedidoRepository pedidoRepository;

    private PedidoItemRepository pedidoItemRepository;

    private ItemRepository itemRepository;

    public PedidoService(MesaRepository mesaRepository, PedidoRepository pedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        final Pedido pedido = Pedido.builder()
                .mesa(retornaMesaPedio(pedidoRequestDTO))
                .situacaoPedido(SituacaoPedido.ABERTO.getCodigo()).build();

        List<PedidoItem> pedidoItems = new ArrayList<>();
        pedidoRequestDTO.getPedidoItens().forEach(pedidoItem -> pedidoItems.add(entityToPedidoItemDto(pedidoItem)));

        pedido.setPedidoItem(pedidoItems);

        return entityToPedidoResponseDTO(pedidoRepository.save(pedido));
    }

    private PedidoItem entityToPedidoItemDto(PedidoItemDTO pedidoItem) {

        Item item = itemRepository.findById(pedidoItem.getItemId()).orElseThrow(() -> new BusinessException("Iten not found!"));

        return PedidoItem.builder()
                .itens(item)
                .quantidade(pedidoItem.getQtd())
                .build();
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
