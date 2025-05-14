package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.demo.dto.ItensDeCardapioDto.AtualizarItemDto;
import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.entities.ItemDeCardapio;
import com.example.demo.mapper.ItensMapper;
import com.example.demo.repository.IItemDeCardapioRepository;
import com.example.demo.repository.IPedidoItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Validated
@Service
public class ItensService {

    @Autowired
    private IItemDeCardapioRepository itemRepository;

    @Autowired
    private IPedidoItemRepository pedidoItemRepository;

    @Autowired
    private ItensMapper itemMapper;

    public ListarItensDto salvar(@Valid CadastrarItensDto itensDto){
        ItemDeCardapio item = itemMapper.toEntity(itensDto);
        return itemMapper.toDto(itemRepository.save(item));
    }

    public List<ListarItensDto> listarItens(){
        return itemMapper.toDtoList(itemRepository.findAll());
    }

    public ListarItensDto obterItemPeloId(Long id){
        ItemDeCardapio item = itemRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
        
        return itemMapper.toDto(item);
    }


    public ListarItensDto atualizarItem(Long id, AtualizarItemDto itemDto){
        ItemDeCardapio item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        if(itemDto.getDescricao() != null){
            item.setDescricao(itemDto.getDescricao());
        }

        if(itemDto.getNome() != null){
            item.setNome(itemDto.getNome());
        }

        if (itemDto.getPreco() != null) {
            item.setPreco(itemDto.getPreco());
        }

        return itemMapper.toDto(itemRepository.save(item));

    }


    public void removerItem(Long id){
        if(!itemRepository.existsById(id)){
            throw new EntityNotFoundException("Item não encontrado");
        }

        boolean existe = pedidoItemRepository.existsByItem_Id(id);

        if (existe) {
            throw new IllegalStateException("O item está associado a um pedido e não pode ser removido.");
        }

        itemRepository.deleteById(id);
    }

}
