package com.project.ordercart.integration;

import com.project.repository.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class IntegrationBaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CardapioItemRepository cardapioItemRepository;

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

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


}
