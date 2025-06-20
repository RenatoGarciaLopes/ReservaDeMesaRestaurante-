package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ItemDeCardapio;

@Repository
public interface IItemDeCardapioRepository extends JpaRepository<ItemDeCardapio, Long> {

    List<ItemDeCardapio> findByAtivo(Boolean ativo);
}
