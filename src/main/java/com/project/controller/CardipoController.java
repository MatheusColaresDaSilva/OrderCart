package com.project.controller;

import com.project.dto.request.CardapioItemRequestDTO;
import com.project.dto.request.CardapioResquestDTO;
import com.project.dto.response.CardapioResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.service.CardapioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cardapio")
public class CardipoController {

    private CardapioService cardapioService;

    public CardipoController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }


    @GetMapping
    public ResponseEntity<ResponseDTO<List<CardapioResponseDTO>>> consultaTodos() {

        List<CardapioResponseDTO> cardapios = cardapioService.consultaTodos();

        return ResponseEntity.ok(new ResponseDTO<>(cardapios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CardapioResponseDTO>> consultaPorId(@PathVariable Long id) {
        final CardapioResponseDTO cardapioResponseDTO = cardapioService.consultaPorId(id);

        return ResponseEntity.ok(new ResponseDTO<>(cardapioResponseDTO));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<CardapioResponseDTO>> cadatrar(@RequestBody CardapioResquestDTO cardapioResquestDTO) {
        final CardapioResponseDTO cardapioResponseDTO = cardapioService.cadastra(cardapioResquestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new ResponseDTO<>(cardapioResponseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> adicionaItem(@PathVariable Long id, @RequestBody CardapioItemRequestDTO cardapioItemRequestDTO) {
        cardapioService.adicionaItem(id, cardapioItemRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarCardapio(@PathVariable Long id) {
        cardapioService.desativaCardapio(id);
        return ResponseEntity.noContent().build();
    }
}
