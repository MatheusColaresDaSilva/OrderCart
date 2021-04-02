package com.project.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "oc_cardapio")
public class Cardapio extends EntidadeBase{

    @Column(length = 200, nullable = false, unique = true)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = true)
    private LocalDateTime dataFim;

    @Singular
    @OneToMany(mappedBy = "cardapio", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CardapioItem> itens = new ArrayList<CardapioItem>();

    @Column(length = 1,nullable = false)
    private String situcaoCardapio;


}
