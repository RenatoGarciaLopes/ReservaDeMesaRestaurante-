package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ItensDeCardapioDto.AtualizarItemDto;
import com.example.demo.dto.ItensDeCardapioDto.CadastrarItensDto;
import com.example.demo.dto.ItensDeCardapioDto.ListarItensDto;
import com.example.demo.entities.Categoria;
import com.example.demo.entities.ItemDeCardapio;
import com.example.demo.mapper.ItensMapper;
import com.example.demo.repository.ICategoriaRepository;
import com.example.demo.repository.IItemDeCardapioRepository;
import com.example.demo.repository.specification.ItemSpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Validated
@Service
public class ItensService {

    @Autowired
    private IItemDeCardapioRepository itemRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private ItensMapper itemMapper;

    
    private final String pastaUpload = System.getProperty("user.dir") + "/uploads/imagens/";

    private String salvarImagemEmDisco(MultipartFile arquivo, Long itemId) throws IOException {
        Files.createDirectories(Paths.get(pastaUpload));
        String nomeOriginal = arquivo.getOriginalFilename();
        String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
        String nomeArquivo = "imagem-item" + "_" + itemId + extensao;
        Path caminhoArquivo = Paths.get(pastaUpload, nomeArquivo);
        arquivo.transferTo(caminhoArquivo.toFile());
        return nomeArquivo;
    }

    public String uploadImagem(MultipartFile imagem, Long itemId) throws IOException {
        return salvarImagemEmDisco(imagem, itemId);
    }

    public ListarItensDto salvar(@Valid CadastrarItensDto itensDto) {
        ItemDeCardapio item = itemMapper.toEntity(itensDto);
        return itemMapper.toDto(itemRepository.save(item));
    }

    public Page<ListarItensDto> listarItens(int pagina, int tamanho,
            String nome, Long categoriaId, Boolean status) { 
        Specification<ItemDeCardapio> specification = Specification.where(ItemSpecification.temNome(nome))
                .and(ItemSpecification.temCategoria(categoriaId))
                .and(ItemSpecification.temStatus(status));

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("nome"));

        return itemRepository.findAll(specification, pageable).map(itemMapper::toDto);
    }

    public ListarItensDto obterItemPeloId(Long id) {
        ItemDeCardapio item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não foi encontrado"));

        return itemMapper.toDto(item);
    }

    public ListarItensDto atualizarItem(Long id, AtualizarItemDto itemDto) {
        ItemDeCardapio item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não foi encontrado"));

        if (itemDto.getDescricao() != null) {
            item.setDescricao(itemDto.getDescricao());
        }

        if (itemDto.getNome() != null) {
            item.setNome(itemDto.getNome());
        }

        if (itemDto.getPreco() != null) {
            item.setPreco(itemDto.getPreco());
        }

        if (itemDto.getImagemUrl() != null) {
            item.setImagemUrl(itemDto.getImagemUrl());
        }

        if (itemDto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrado"));

            item.setCategoria(categoria);
        }

        return itemMapper.toDto(itemRepository.save(item));
    }

    public void inativarItem(Long id) {
        ItemDeCardapio item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não foi encontrado"));

        item.setAtivo(false);
        itemRepository.save(item);
    }

    public void reativarItem(Long id) {
        ItemDeCardapio item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não foi encontrado"));

        if (item.getAtivo() == true) {
            throw new IllegalStateException("Item de cardápio já está ativo");
        }

        item.setAtivo(true);
        itemRepository.save(item);
    }
}
