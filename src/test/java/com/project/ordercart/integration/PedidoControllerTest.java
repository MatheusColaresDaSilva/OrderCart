package com.project.ordercart.integration;

import com.project.dto.request.PedidoRequestDTO;
import com.project.enumerator.SituacaoPedido;
import com.project.ordercart.utils.OrderCartTestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PedidoControllerTest extends IntegrationBaseTest{

    private final String URL = "/api/v1/pedido";

    @Test
    public void criarPedidoInicialTest() {
        createMesa();
        final PedidoRequestDTO pedidoRequestDTO = criaPedidoRequestDTO();
        OrderCartTestUtil.createRequestPost(URL, pedidoRequestDTO, HttpStatus.CREATED)
        .root("dados")
                .body("idPedido", Matchers.greaterThan(0))
                .body("idMesa", Matchers.equalTo(1))
                .body("situacaoPedido", Matchers.is(SituacaoPedido.ABERTO.getCodigo()));
    }

    private PedidoRequestDTO criaPedidoRequestDTO() {
        return PedidoRequestDTO.builder()
                .mesaId(mesaRepository.findByNumeroMesa(Integer.valueOf(1)).get().getId()).build();
    }
}
