package com.project.repository;

import com.project.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
}
