package com.example.demo.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entities.Funcionario;
import com.example.demo.enums.Cargo;

public class FuncionarioSpecification {

    public static Specification<Funcionario> temNome(String nome) {
        return (root, query, cb) -> 
            nome == null || nome.isBlank() ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Funcionario> temcargo(Cargo cargo) {
        return (root, query, cb) -> 
            cargo == null ? null : cb.equal(root.get("cargo"), cargo);
    }

    public static Specification<Funcionario> isAtivo(Boolean ativo) {
        return (root, query, cb) ->
            ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }
}
