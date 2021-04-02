package com.project.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "oc_pedido_item")
public class PedidoItem extends EntidadeBase{

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item itens;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(precision = 10, nullable = false)
    private Integer quantidade;

}
