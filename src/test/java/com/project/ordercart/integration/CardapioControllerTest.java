package com.project.ordercart.integration;

import com.project.dto.request.CardapioItemRequestDTO;
import com.project.dto.request.CardapioResquestDTO;
import com.project.enumerator.SituacaoCardapio;
import com.project.ordercart.utils.OrderCartTestUtil;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CardapioControllerTest extends IntegrationBaseTest{

    private final String URL = "/api/v1/cardapio";

    @Test
    public void cadastraCardapioSemItemComSucesso() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioSemItemResquestDTO();
        OrderCartTestUtil.createRequestPost(URL,cardapioResquestDTO, HttpStatus.CREATED)
          .root("dados")
                .body("id",Matchers.greaterThan(0))
                .body("descricao", Matchers.equalTo("CardapioA"))
                .body("dataInicio",Matchers.equalTo("2021-02-01T00:00:00"))
                .body("dataFim",Matchers.equalTo("2021-03-01T00:00:00"))
                .body("situcaoCardapio", Matchers.is(SituacaoCardapio.ATIVO.getCodigo()));

    }

    //@Test
    public void cadastraCardapioComItemComSucesso() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        OrderCartTestUtil.createRequestPost(URL,cardapioResquestDTO, HttpStatus.CREATED)
                .root("dados")
                .body("id",Matchers.greaterThan(0))
                .body("descricao", Matchers.equalTo("CardapioA"))
                .body("dataInicio",Matchers.equalTo("2021-02-01T00:00:00"))
                .body("dataFim",Matchers.equalTo("2021-03-01T00:00:00"))
                .body("itens",null)
                .body("situcaoCardapio", Matchers.is(SituacaoCardapio.ATIVO.getCodigo()));

    }

    private CardapioResquestDTO criaCardapioSemItemResquestDTO() {
        return CardapioResquestDTO.builder()
                .descricao("CardapioA")
                .dataInicio(LocalDateTime.of(2021, 2, 1, 0, 0))
                .dataFim(LocalDateTime.of(2021, 3, 1, 0, 0))
                .itens(new ArrayList<>())
                .situcaoCardapio(SituacaoCardapio.ATIVO.getCodigo()).build();
    }

    private CardapioResquestDTO criaCardapioComItemResquestDTO() {
        final CardapioItemRequestDTO cardapioItemRequestDTO = CardapioItemRequestDTO.builder()
                .valor(BigDecimal.valueOf(30.0)).build();

        return CardapioResquestDTO.builder()
                .descricao("CardapioA")
                .dataInicio(LocalDateTime.of(2021, 2, 1, 0, 0))
                .dataFim(LocalDateTime.of(2021, 3, 1, 0, 0))
                .itens(Arrays.asList(cardapioItemRequestDTO))
                .situcaoCardapio(SituacaoCardapio.ATIVO.getCodigo()).build();
    }
}
