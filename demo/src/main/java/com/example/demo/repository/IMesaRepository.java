package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Mesa;

@Repository
public interface IMesaRepository extends JpaRepository<Mesa, Long>, JpaSpecificationExecutor<Mesa> {

}
