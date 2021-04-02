package com.project.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "oc_item")
public class Item extends EntidadeBase {

    @Column(length = 200, nullable = false)
    private String descricao;
}
