package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{

}
