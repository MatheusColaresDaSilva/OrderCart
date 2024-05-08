package com.project.repository;

import com.project.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {

    List<Cardapio> findBySitucaoCardapio(String situcaoCardapio);

    @Query("SELECT c FROM Cardapio c WHERE c.id <> :id")
    List<Cardapio> findAllActiveCartsExceptFor(@Param("id") Long id);

}
