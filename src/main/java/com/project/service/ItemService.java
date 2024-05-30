package com.project.service;

import com.project.dto.request.ItemRequestDTO;
import com.project.dto.response.ItemResponseDTO;
import com.project.entity.Item;
import com.project.exception.BusinessException;
import com.project.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) { this.itemRepository = itemRepository; }

    public Page<ItemResponseDTO> consultaTodos(Pageable page) {
        Page<Item> items = itemRepository.findAll(page);
        List<ItemResponseDTO> itemsResponseDTO = new ArrayList<ItemResponseDTO>();
        items.forEach(item -> itemsResponseDTO.add(entityToItemDto(item)));

        return new PageImpl<>(itemsResponseDTO, page, items.getTotalElements());
    }

    public ItemResponseDTO consultaById(Long id) {
        final Item item = buscaPorId(id);
        return entityToItemDto(item);
    }

    @Transactional
    public ItemResponseDTO cadastraItem(ItemRequestDTO itemRequestDTO) {
        Item item = itemDtoToEntity(new Item(), itemRequestDTO);
        return entityToItemDto(itemRepository.save(item));
    }

    @Transactional
    public ItemResponseDTO atualizaItem(Long id, ItemRequestDTO itemRequestDTO) {
        Item item = itemDtoToEntity(buscaPorId(id), itemRequestDTO);
        return entityToItemDto(itemRepository.save(item));
    }

    private Item itemDtoToEntity(Item item, ItemRequestDTO itemRequestDTO) {
        item.setDescricao(itemRequestDTO.getDescription());
        return item;
    }

    private ItemResponseDTO entityToItemDto(Item item) {
        return ItemResponseDTO.builder()
                        .id(item.getId())
                        .description(item.getDescricao())
                        .build();
    }

    public Item buscaPorId(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new BusinessException("Item não encontrado"));
    }
}
