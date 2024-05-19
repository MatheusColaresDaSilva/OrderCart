package com.project.ordercart.integration;

import com.project.dto.request.ItemRequestDTO;
import com.project.dto.response.ItemResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.entity.Item;
import com.project.ordercart.utils.OrderCartTestUtil;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.mapper.TypeRef;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ItemControllerTest extends IntegrationBaseTest{

    private final String URL = "/api/v1/item";

    @BeforeEach
    public void  buildItem() {
       createItem();
    }

    @Test
    public void cadastraItemComSucesso() {
        ItemRequestDTO itemRequestDTO = criaItemResquestDTO();
        OrderCartTestUtil.createRequestPost(URL,itemRequestDTO, HttpStatus.CREATED)
          .root("dados")
                .body("descricao", Matchers.equalTo("Flat Iron"));
    }

    @Test
    public void consultaItemPorIdSucesso() {
        Item item = itemRepository.findByDescricao("Frango a Passarinho").get();
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", item.getId())
                .build();

        OrderCartTestUtil.createRequestGet(URL+"/{id}",requestSpecification, HttpStatus.OK);
    }

    @Test
    public void consultaItemPorIdItemNaoExistente() {
       RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addPathParam("id", 10)
                .build();

        ResponseDTO<ItemResponseDTO> response = OrderCartTestUtil.createRequestGet(URL + "/{id}", requestSpecification, HttpStatus.BAD_REQUEST)
                .extract().body().as(new TypeRef<ResponseDTO<ItemResponseDTO>>() {});

        assertMensagemErro(response, "Item não encontrado");
    }

    @Test
    public void atualizaItem() {
        Item item = itemRepository.findByDescricao("Frango a Passarinho").get();
        final ItemRequestDTO itemRequestDTO = criaItemResquestDTO();

        OrderCartTestUtil.createRequestPut(URL + "/" + item.getId(), itemRequestDTO, HttpStatus.OK)
                .extract().body().as(new TypeRef<ResponseDTO<ItemResponseDTO>>() {});

        final Item itemApostualizar = itemRepository.findById(item.getId()).get();
        MatcherAssert.assertThat(itemApostualizar.getDescricao(),Matchers.equalTo("Flat Iron"));
    }

    private ItemRequestDTO criaItemResquestDTO() {
        return ItemRequestDTO.builder()
                .description("Flat Iron").build();
    }

}
