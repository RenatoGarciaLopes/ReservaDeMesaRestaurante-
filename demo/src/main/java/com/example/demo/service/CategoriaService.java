package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoriaDto.CadastrarCategoriaDto;
import com.example.demo.dto.CategoriaDto.ListarCategoriaDto;
import com.example.demo.entities.Categoria;
import com.example.demo.mapper.CategoriaMapper;
import com.example.demo.repository.ICategoriaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Transactional
    public ListarCategoriaDto salvar(CadastrarCategoriaDto dto) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNomeIgnoreCase(dto.getNome());

        if (categoriaExistente.isPresent()) {
            throw new IllegalStateException("Já existe uma categoria com esse nome.");
        }

        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    public List<ListarCategoriaDto> listarTodasCategorias() {
        return categoriaMapper.toListDto(categoriaRepository.findAll());
    }

    public List<ListarCategoriaDto> listarCategoriasPorStatus(Boolean ativo) {
        return categoriaMapper.toListDto(categoriaRepository.findByAtivo(ativo));
    }

    public ListarCategoriaDto obterCategoriaPeloId(long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrada"));

        return categoriaMapper.toDto(categoria);
    }

    @Transactional
    public ListarCategoriaDto atualizarCategoria(Long id, CadastrarCategoriaDto dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrada"));

        categoria.setNome(dto.getNome());
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Transactional
    public void inativarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrada"));

        categoria.setAtivo(false);
        categoriaRepository.save(categoria);
    }

    @Transactional
    public void reativarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrada"));

        if (categoria.getStatus() == true) {
            throw new IllegalStateException("Categoria já está ativa");
        }

        categoria.setStatus(true);
        categoriaRepository.save(categoria);
    }
}
