package com.project.ordercart.integration;

import com.project.dto.request.CardapioItemRequestDTO;
import com.project.dto.request.CardapioResquestDTO;
import com.project.entity.Item;
import com.project.enumerator.SituacaoCardapio;
import com.project.ordercart.utils.OrderCartTestUtil;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CardapioControllerTest extends IntegrationBaseTest{

    private final String URL = "/api/v1/cardapio";

    @BeforeEach
    public void  buildItem() {
       createItem();
    }

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

    @Test
    public void cadastraCardapioComItemComSucesso() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        BigDecimal valor = BigDecimal.valueOf(30);
        OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .root("dados")
                .body("id",Matchers.greaterThan(0))
                .body("descricao", Matchers.equalTo("CardapioA"))
                .body("dataInicio",Matchers.equalTo("2021-02-01T00:00:00"))
                .body("dataFim",Matchers.equalTo("2021-03-01T00:00:00"))
                .body("itens[0].idCardapio",Matchers.greaterThan(0))
                        .body("itens[0].idItem",Matchers.greaterThan(0))
                        .body("itens[0].valor", CoreMatchers.equalTo(valor.intValue()))
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
        final CardapioItemRequestDTO cardapioItemRequestDTO = createCardapioItemRequestDTO();

        return CardapioResquestDTO.builder()
                .descricao("CardapioA")
                .dataInicio(LocalDateTime.of(2021, 2, 1, 0, 0))
                .dataFim(LocalDateTime.of(2021, 3, 1, 0, 0))
                .itens(Arrays.asList(cardapioItemRequestDTO))
                .situcaoCardapio(SituacaoCardapio.ATIVO.getCodigo()).build();
    }

    private CardapioItemRequestDTO createCardapioItemRequestDTO() {
    return CardapioItemRequestDTO.builder()
                .item(itemRepository.findByDescricao("Frango a Passarinho").get().getId())
                .valor(BigDecimal.valueOf(30)).build();
    }

    private void createItem() {
        Item item1 = Item.builder().descricao("Frango a Passarinho").build();
        itemRepository.save(item1);
    }
}
