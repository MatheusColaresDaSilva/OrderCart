package com.project.repository;

import com.project.entity.CardapioItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardapioItemRepository extends JpaRepository<CardapioItem, Long> {
}
