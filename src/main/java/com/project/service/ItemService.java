package com.project.service;

import com.project.dto.request.ItemRequestDTO;
import com.project.dto.response.ItemResponseDTO;
import com.project.entity.Item;
import com.project.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) { this.itemRepository = itemRepository; }

    public List<ItemResponseDTO> consultaTodos() {
        final List<Item> items = itemRepository.findAll();
        List<ItemResponseDTO> itemsResponseDTO = new ArrayList<ItemResponseDTO>();
        items.forEach(item -> itemsResponseDTO.add(entityToItemDto(item)));

        return itemsResponseDTO;
    }

    public ItemResponseDTO cadastraItem(ItemRequestDTO itemRequestDTO) {
        Item item = itemDtoToEntity(new Item(), itemRequestDTO);
        return entityToItemDto(itemRepository.save(item));
    }

    private Item itemDtoToEntity(Item item, ItemRequestDTO itemRequestDTO) {
        item.setDescricao(itemRequestDTO.getDescricao());
        return item;
    }

    private ItemResponseDTO entityToItemDto(Item item) {
        return ItemResponseDTO.builder()
                        .id(item.getId())
                        .descricao(item.getDescricao())
                        .build();
    }
}
