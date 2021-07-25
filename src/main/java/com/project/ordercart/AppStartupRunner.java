package com.project.ordercart;

import com.project.entity.*;
import com.project.enumerator.SituacaoCardapio;
import com.project.enumerator.SituacaoPedido;
import com.project.repository.CardapioRepository;
import com.project.repository.ItemRepository;
import com.project.repository.MesaRepository;
import com.project.repository.PedidoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//@Component
public class AppStartupRunner implements ApplicationRunner {

    private MesaRepository mesaRepository;
    private ItemRepository itemRepository;
    private CardapioRepository cardapioRepository;
    private PedidoRepository pedidoRepository;

    public AppStartupRunner(MesaRepository mesaRepository, ItemRepository itemRepository, CardapioRepository cardapioRepository,PedidoRepository pedidoRepository) {
        this.mesaRepository = mesaRepository;
        this.itemRepository = itemRepository;
        this.cardapioRepository = cardapioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("AppStratupRunner");

        Mesa mesa1 = Mesa.builder().numeroMesa(1).build();
        Mesa mesa2 = Mesa.builder().numeroMesa(2).build();
        mesaRepository.save(mesa1);
        mesaRepository.save(mesa2);

        Item item1 = Item.builder().descricao("Frango a Passarinho").build();
        Item item2 = Item.builder().descricao("Batata Frita").build();
        Item item3 = Item.builder().descricao("Cerveja Eden Pilsen").build();

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);


        Cardapio cardapio1 = Cardapio.builder()
                                    .descricao("Cardápio 01/02/2021 até 20/02/2021")
                                    .dataInicio(LocalDateTime.of(2021,2,1,0,0,0))
                                    .situcaoCardapio(SituacaoCardapio.ATIVO.getCodigo()).build();

        List<CardapioItem> cardapioItems = Arrays.asList(CardapioItem.builder()
                        .cardapio(cardapio1)
                        .item(item1)
                        .valor(new BigDecimal(35)).build(),
                CardapioItem.builder()
                        .cardapio(cardapio1)
                        .item(item2)
                        .valor(new BigDecimal(20.5)).build(),
                CardapioItem.builder()
                        .cardapio(cardapio1)
                        .item(item3)
                        .valor(new BigDecimal(6.90)).build());

        cardapio1.setItens(cardapioItems);
        cardapioRepository.save(cardapio1);

        Pedido pedido = Pedido.builder()
                                .mesa(mesa1)
                                .situacaoPedido(SituacaoPedido.ABERTO.getCodigo()).build();

        List<PedidoItem> pedidoItems = Arrays.asList(PedidoItem.builder()
                        .pedido(pedido)
                        .itens(cardapioItems.get(0).getItem())
                        .quantidade(1)
                        .valor(cardapioItems.get(0).getValor()).build(),
                PedidoItem.builder()
                        .pedido(pedido)
                        .itens(cardapioItems.get(1).getItem())
                        .quantidade(2)
                        .valor(cardapioItems.get(1).getValor()).build(),
                PedidoItem.builder()
                        .pedido(pedido)
                        .itens(cardapioItems.get(2).getItem())
                        .quantidade(5)
                        .valor(cardapioItems.get(2).getValor()).build());

        pedido.setPedidoItem(pedidoItems);
        pedidoRepository.save(pedido);

    }
}
