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

    @PostMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarCardapio(@PathVariable Long id) {
        cardapioService.desativaCardapio(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ativar")
    public ResponseEntity<Void> ativarCardapio(@PathVariable Long id) {
        cardapioService.ativaCardapio(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CardapioResponseDTO>> atualiza(@PathVariable Long id, @RequestBody CardapioResquestDTO cardapioResquestDTO) {
        final CardapioResponseDTO cardapioResponseDTO = cardapioService.atualiza(id, cardapioResquestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(cardapioResponseDTO));
    }

}
