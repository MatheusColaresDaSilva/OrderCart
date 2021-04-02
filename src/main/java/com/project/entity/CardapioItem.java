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
@Table(name = "oc_cardapio_item")
public class CardapioItem extends EntidadeBase{

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_cardapio", nullable = false)
    private Cardapio cardapio;

    @OneToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal valor;
}
