package com.example.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.Pedido;
import com.example.demo.entities.PedidoItem;
import com.example.demo.enums.StatusPedido;

import jakarta.persistence.criteria.Join;

public class PedidoSpecification {

    public static Specification<Pedido> temStatus(StatusPedido status) {
        return (root, query, cb) -> 
            status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Pedido> temItemDeCardapio(String nomeItem) {
        return (root, query, cb) -> {
            Join<Pedido, PedidoItem> join = root.join("pedidoItens");
            return nomeItem == null || nomeItem.isBlank() ? null
                    : cb.like(cb.lower(join.get("item").get("nome")), "%" + nomeItem.toLowerCase() + "%");
        };
    }
}
