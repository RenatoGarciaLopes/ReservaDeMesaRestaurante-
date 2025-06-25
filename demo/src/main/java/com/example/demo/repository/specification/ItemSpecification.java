package com.example.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.ItemDeCardapio;

public class ItemSpecification {

    public static Specification<ItemDeCardapio> temNome(String nome) {
        return (root, query, cb) -> 
            nome == null || nome.isBlank() ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<ItemDeCardapio> temCategoria(Long categoriaId) {
        return (root, query, cb) -> 
            categoriaId == null ? null : cb.equal(root.get("categoria").get("id"), categoriaId);
    }

    public static Specification<ItemDeCardapio> temStatus(Boolean status) {
        return (root, query, cb) -> 
            status == null ? null : cb.equal(root.get("ativo"), status);
    }
}
