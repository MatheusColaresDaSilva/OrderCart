package com.project.controller;

import com.project.dto.request.ItemRequestDTO;
import com.project.dto.response.ItemResponseDTO;
import com.project.dto.response.ResponseDTO;
import com.project.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController extends ControllerBase{

    private ItemService itemService;

    public ItemController(ItemService itemService) { this.itemService = itemService; }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ItemResponseDTO>>> consultaTodos() {
        final List<ItemResponseDTO>  itemsResponseDTO = itemService.consultaTodos();
        return ResponseEntity.ok(new ResponseDTO<>(itemsResponseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ItemResponseDTO>> consultaById(@PathVariable Long id) {
        final ItemResponseDTO itemResponseDTO = itemService.consultaById(id);
        return ResponseEntity.ok(new ResponseDTO<>(itemResponseDTO));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ItemResponseDTO>> cadastraItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        final ItemResponseDTO itemResponseDTO = itemService.cadastraItem(itemRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new ResponseDTO<>(itemResponseDTO));
    }
}
