package com.project.service;

import com.project.dto.request.CardapioItemRequestDTO;
import com.project.dto.request.CardapioResquestDTO;
import com.project.dto.response.CardapioItemResponseDTO;
import com.project.dto.response.CardapioResponseDTO;
import com.project.entity.Cardapio;
import com.project.entity.CardapioItem;
import com.project.entity.Item;
import com.project.enumerator.SituacaoCardapio;
import com.project.exception.BusinessException;
import com.project.repository.CardapioRepository;
import com.project.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CardapioService {

    private CardapioRepository cardapioRepository;
    private ItemRepository itemRepository;

    public CardapioService(CardapioRepository cardapioRepository, ItemRepository itemRepository) {
        this.cardapioRepository = cardapioRepository;
        this.itemRepository = itemRepository;
    }

    public List<CardapioResponseDTO> consultaTodos() {
        final List<Cardapio> cardapios = cardapioRepository.findAll();
        final List<CardapioResponseDTO> cardapiosResponseDTO = new ArrayList<>();

        cardapios.forEach(cardapio -> cardapiosResponseDTO.add(entityToCardapioDto(cardapio)));

        return cardapiosResponseDTO;
    }

    public CardapioResponseDTO consultaPorId(Long id) {
        final Cardapio cardapio = buscaPorId(id);
        return entityToCardapioDto(cardapio);
    }

    @Transactional
    public CardapioResponseDTO cadastra(CardapioResquestDTO cardapioResquestDTO) {
        final Cardapio cardapio = cardapioDtoToEntity(new Cardapio(), cardapioResquestDTO);
        return entityToCardapioDto(cardapioRepository.save(cardapio));
    }

    @Transactional
    public CardapioResponseDTO atualiza(Long idCardapio, CardapioResquestDTO cardapioResquestDTO) {
        validaAtualizacao(idCardapio);
        Cardapio cardapio = cardapioDtoToEntity(buscaPorId(idCardapio), cardapioResquestDTO);

        return entityToCardapioDto(cardapioRepository.save(cardapio));
    }

    @Transactional
    public void desativaCardapio(Long idCardapio) {
        Cardapio cardapio = buscaPorId(idCardapio);

        if(SituacaoCardapio.DESABILITADO.getCodigo().equals(cardapio.getSitucaoCardapio())) {
            throw new BusinessException("Cardapio já está Desativado");
        }

        cardapio.setDataFim(LocalDateTime.now());
        cardapio.setSitucaoCardapio(SituacaoCardapio.DESABILITADO.getCodigo());
        cardapioRepository.save(cardapio);
    }

    @Transactional
    public void ativaCardapio(Long idCardapio) {
        Cardapio cardapio = buscaPorId(idCardapio);

        if(SituacaoCardapio.ATIVO.getCodigo().equals(cardapio.getSitucaoCardapio())) {
            throw new BusinessException("Cardapio já está Ativado");
        }

        cardapio.setDataFim(null);
        cardapio.setSitucaoCardapio(SituacaoCardapio.ATIVO.getCodigo());
        cardapioRepository.save(cardapio);
    }

    private Cardapio cardapioDtoToEntity(Cardapio cardapio, CardapioResquestDTO cardapioResquestDTO) {
        cardapio.setDescricao(cardapioResquestDTO.getDescricao());
        cardapio.setDataInicio(cardapioResquestDTO.getDataInicio());
        cardapio.setDataFim(cardapioResquestDTO.getDataFim());
        cardapio.setSitucaoCardapio(cardapioResquestDTO.getSitucaoCardapio());


        List<CardapioItem> cardapioItens = new ArrayList<>();
        cardapioResquestDTO.getItens().forEach(cardapioItemRequestDTO -> cardapioItens.add(cardapioItemDtoToEntity(cardapio,cardapioItemRequestDTO)));

        cardapio.getItens().clear();
        cardapio.getItens().addAll(cardapioItens);
        return cardapio;
    }

    private CardapioItem cardapioItemDtoToEntity(Cardapio cardapio, CardapioItemRequestDTO cardapioItemRequestDTO) {
       return CardapioItem.builder()
                    .cardapio(cardapio)
                    .item(buscaItemPorId(cardapioItemRequestDTO.getItem()))
                    .valor(cardapioItemRequestDTO.getValor())
                    .build();
    }

    private CardapioResponseDTO entityToCardapioDto(Cardapio cardapio) {
        List<CardapioItemResponseDTO> cardapioItensResponseDTO = new ArrayList<>();
        cardapio.getItens().forEach(cardapioItem -> cardapioItensResponseDTO.add(entityToCardapioItemDto(cardapioItem)));

        return CardapioResponseDTO.builder()
                                .id(cardapio.getId())
                                .dataInicio(cardapio.getDataInicio())
                                .dataFim(cardapio.getDataFim())
                                .descricao(cardapio.getDescricao())
                                .itens(cardapioItensResponseDTO)
                                .situcaoCardapio(cardapio.getSitucaoCardapio()).build();
    }

    private CardapioItemResponseDTO entityToCardapioItemDto(CardapioItem cardapioItem) {
        return CardapioItemResponseDTO.builder()
                                        .idCardapio(cardapioItem.getCardapio().getId())
                                        .idItem(cardapioItem.getItem().getId())
                                        .valor(cardapioItem.getValor()).build();
    }

    private Cardapio buscaPorId(Long id) {
        return cardapioRepository.findById(id).orElseThrow(() -> new BusinessException("Cardápio não encontrado"));
    }

    private Item buscaItemPorId(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new BusinessException("Item não encontrado"));
    }

    private void validaAtualizacao(Long idCardapio) {
        Cardapio cardapio = buscaPorId(idCardapio);
        if(isNull(cardapio)) {
           throw new BusinessException("Cardápio não encontrado");
        }
    }
}
