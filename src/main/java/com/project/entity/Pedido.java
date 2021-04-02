package com.project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "oc_pedido")
public class Pedido extends EntidadeBase {

    @OneToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;

    @OneToMany(mappedBy="pedido", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoItem> pedidoItem;

    @Column(length = 1, nullable = false)
    private String situacaoPedido;
}
