package com.project.controller;

import com.project.dto.request.CardapioItemRequestDTO;
import com.project.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> adicionaItem(@PathVariable Long id, @RequestBody CardapioItemRequestDTO cardapioItemRequestDTO) {
//        cardapioService.adicionaItem(id, cardapioItemRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
