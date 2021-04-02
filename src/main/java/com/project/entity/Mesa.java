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
@Table(name = "oc_mesa")
public class Mesa extends EntidadeBase {

    @Column(precision = 5, nullable = false)
    private Integer numeroMesa;
}
