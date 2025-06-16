package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "clientes")
@Entity
public class Cliente {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String nome;

   @Column(nullable = false, unique = true)
   private String cpf;

   @Column(nullable = false)
   private String email;

   @Column(nullable = false)
   private String telefone;

   @Column(nullable = false, updatable = false)
   private LocalDateTime dataCadastro = LocalDateTime.now();

   @Column(columnDefinition = "TEXT")
   private String observacoes;
}