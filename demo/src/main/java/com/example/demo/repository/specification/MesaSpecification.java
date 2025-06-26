package com.example.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.Mesa;
import com.example.demo.enums.StatusMesa;

public class MesaSpecification {

    public static Specification<Mesa> temStatus(StatusMesa status) {
        return (root, query, cb) -> 
            status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Mesa> temCapacidade(Integer capacidade) {
        return (root, query, cb) -> 
            capacidade == null ? null : cb.greaterThanOrEqualTo(root.get("capacidade"), capacidade);
    }

    public static Specification<Mesa> isAtivo(Boolean ativo) {
        return (root, query, cb) -> 
            ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }
}
