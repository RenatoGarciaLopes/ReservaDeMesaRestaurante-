package com.example.demo.mapper.Utils;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Categoria;
import com.example.demo.repository.ICategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ItemHelperMapper {

    @Autowired
    protected ICategoriaRepository categoriaRepository;

    @Named("buscaCategoriaPorId")
    public Categoria buscaReservaPorId(Long reservaId) {
        Categoria categoria = categoriaRepository.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não foi encontrada"));

        return categoria;
    }
}
