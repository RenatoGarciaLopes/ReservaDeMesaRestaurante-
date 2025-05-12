package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GenerationType;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity


public class Cliente{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
   private Long id;

   @Column(nullable = false)
   private String nome;

   @Column(nullable = false)
   private String email;

   @Column(nullable = false)
   private String telefone;
}