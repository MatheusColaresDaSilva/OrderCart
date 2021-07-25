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


}
