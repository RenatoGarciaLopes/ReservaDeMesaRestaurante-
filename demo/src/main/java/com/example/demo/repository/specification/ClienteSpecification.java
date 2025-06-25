package com.example.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.Cliente;

public class ClienteSpecification {

    public static Specification<Cliente> temNome(String nome) {
        return (root, query, cb) -> 
            nome == null || nome.isBlank() ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Cliente> isAtivo(Boolean ativo) {
        return (root, query, cb) ->
           ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }
}
