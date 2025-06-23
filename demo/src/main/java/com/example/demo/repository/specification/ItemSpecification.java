package com.example.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.ItemDeCardapio;

public class ItemSpecification {

    public static Specification<ItemDeCardapio> temNome(String nome) {
        return (root, query, cb) -> {
            if (nome == null) return null;
            String nomeFormatado = nome.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("nome")), nomeFormatado);
        };
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
