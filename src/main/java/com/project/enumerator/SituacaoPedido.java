package com.project.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SituacaoPedido {

    ABERTO("A"),
    FECHADO("F"),
    PAGO("P");

    private String codigo;

    public static SituacaoPedido findBy(String codigo) {
        for (SituacaoPedido situacaoPedido : values()) {
            if (situacaoPedido.getCodigo().equals(codigo)) {
                return situacaoPedido;
            }
        }
        return null;
    }
}
