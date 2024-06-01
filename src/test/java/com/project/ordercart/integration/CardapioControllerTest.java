package com.project.ordercart.integration;

import com.project.dto.request.CardapioItemRequestDTO;
import com.project.dto.request.CardapioResquestDTO;
import com.project.dto.response.CardapioResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.entity.Cardapio;
import com.project.enumerator.SituacaoCardapio;
import com.project.ordercart.utils.OrderCartTestUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.mapper.TypeRef;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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

    @Test
    public void consultaCardapioPorIdSucesso() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", response.getContent().getId())
                .build();

        OrderCartTestUtil.createRequestGet(URL+"/{id}",requestSpecification, HttpStatus.OK);
    }

    @Test
    public void consultaCardapioPorIdCardapioNaoExistente() {
       RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", 1)
                .build();

        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestGet(URL + "/{id}", requestSpecification, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        assertMensagemErro(response, "Cardápio não encontrado");
    }

    @Test
    public void atualizaCardapio() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        cardapioResquestDTO.setDescricao("Descrição Nova Atualizado");
        cardapioResquestDTO.setDataInicio(LocalDateTime.of(2021, 4, 1, 0, 0));
        cardapioResquestDTO.setDataFim(LocalDateTime.of(2021, 5, 1, 0, 0));

        OrderCartTestUtil.createRequestPut(URL+"/"+response.getContent().getId(),cardapioResquestDTO, HttpStatus.OK)
                .root("dados")
                .body("id",Matchers.is(response.getContent().getId().intValue()))
                .body("descricao", Matchers.equalTo("Descrição Nova Atualizado"))
                .body("dataInicio",Matchers.equalTo("2021-04-01T00:00:00"))
                .body("dataFim",Matchers.equalTo("2021-05-01T00:00:00"));
    }

    @Test
    public void desativarCardapio() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        OrderCartTestUtil.createRequestPost(URL+"/"+response.getContent().getId()+"/desativar", HttpStatus.NO_CONTENT);

        final Cardapio cardapio = cardapioRepository.findById(response.getContent().getId()).get();
        MatcherAssert.assertThat(cardapio.getSitucaoCardapio(), Matchers.equalTo(SituacaoCardapio.DESABILITADO.getCodigo()));
    }

    @Test
    public void ativarCardapio() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        OrderCartTestUtil.createRequestPost(URL+"/"+response.getContent().getId()+"/desativar", HttpStatus.NO_CONTENT);
        OrderCartTestUtil.createRequestPost(URL+"/"+response.getContent().getId()+"/ativar", HttpStatus.NO_CONTENT);

        final Cardapio cardapio = cardapioRepository.findById(response.getContent().getId()).get();
        MatcherAssert.assertThat(cardapio.getSitucaoCardapio(), Matchers.equalTo(SituacaoCardapio.ATIVO.getCodigo()));
    }

    @Test
    public void desativarCardapioJaDesativado() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        OrderCartTestUtil.createRequestPost(URL+"/"+response.getContent().getId()+"/desativar", HttpStatus.NO_CONTENT);
        ResponseDTO<CardapioResponseDTO> responseAposDestivar = OrderCartTestUtil.createRequestPost(URL + "/" + response.getContent().getId() + "/desativar", HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {
                });

        assertMensagemErro(responseAposDestivar, "Cardapio já está Desativado");
    }

    @Test
    public void ativarCardapioJaAtivado() {
        CardapioResquestDTO cardapioResquestDTO = criaCardapioComItemResquestDTO();
        ResponseDTO<CardapioResponseDTO> response = OrderCartTestUtil.createRequestPost(URL, cardapioResquestDTO, HttpStatus.CREATED)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {});

        ResponseDTO<CardapioResponseDTO> responseAposDestivar = OrderCartTestUtil.createRequestPost(URL + "/" + response.getContent().getId() + "/ativar", HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<CardapioResponseDTO>>() {
                });

        assertMensagemErro(responseAposDestivar, "Cardapio já está Ativado");
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

}
