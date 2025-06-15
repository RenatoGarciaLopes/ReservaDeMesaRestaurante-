package com.example.demo.service;

import java.util.List;

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
        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    public List<ListarCategoriaDto> listarCliente() {
        return categoriaMapper.toListDto(categoriaRepository.findAll());
    }

    public ListarCategoriaDto obterCategoriaPeloId(long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrada"));

        return categoriaMapper.toDto(categoria);
    }
}
