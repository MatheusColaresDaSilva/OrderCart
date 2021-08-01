package com.project.ordercart.integration;

import com.project.dto.response.ErroResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.entity.Item;
import com.project.repository.*;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class IntegrationBaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected CardapioItemRepository cardapioItemRepository;

    @Autowired
    protected CardapioRepository cardapioRepository;

    @Autowired
    protected ItemRepository itemRepository;

    @Autowired
    protected MesaRepository mesaRepository;

    @Autowired
    protected PedidoItemRepository pedidoItemRepository;

    @Autowired
    protected PedidoRepository pedidoRepository;

    @BeforeEach
    public void before() {

        RestAssured.port = port;

        pedidoItemRepository.deleteAll();
        pedidoRepository.deleteAll();
        cardapioItemRepository.deleteAll();
        cardapioRepository.deleteAll();
        itemRepository.deleteAll();
        mesaRepository.deleteAll();

    }

    @AfterEach
    public void after() {
        pedidoItemRepository.deleteAll();
        pedidoRepository.deleteAll();
        cardapioItemRepository.deleteAll();
        cardapioRepository.deleteAll();
        itemRepository.deleteAll();
        mesaRepository.deleteAll();
    }

    protected  <T> void assertMensagemErro(ResponseDTO<T> response, String mesagem) {
        MatcherAssert.assertThat(response.getErros(), Matchers.hasSize(1));
        ErroResponseDTO erroResponseDTO = response.getErros().stream().findFirst().get();
        MatcherAssert.assertThat(erroResponseDTO.getMensagem(),Matchers.equalTo(mesagem));
    }

    protected void createItem() {
        Item item1 = Item.builder().descricao("Frango a Passarinho").build();
        itemRepository.save(item1);
    }
}
