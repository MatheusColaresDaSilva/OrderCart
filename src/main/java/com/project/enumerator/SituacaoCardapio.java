package com.project.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SituacaoCardapio {

    ATIVO("A"),
    DESABILITADO("B");

    private String codigo;

    public static SituacaoCardapio findBy(String codigo) {
        for (SituacaoCardapio situacaoCardapio : values()) {
            if (situacaoCardapio.getCodigo().equals(codigo)) {
                return situacaoCardapio;
            }
        }

        return null;
    }
}
