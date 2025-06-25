package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FuncionarioDto.AtualizarFuncionarioDto;
import com.example.demo.dto.FuncionarioDto.CadastrarFuncionarioDto;
import com.example.demo.dto.FuncionarioDto.ListarFuncionarioDto;
import com.example.demo.entities.Funcionario;
import com.example.demo.enums.Cargo;
import com.example.demo.mapper.FuncionarioMapper;
import com.example.demo.repository.IFuncionarioRepository;
import com.example.demo.repository.specification.FuncionarioSpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService {

    @Autowired
    private IFuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Transactional
    public ListarFuncionarioDto salvar(CadastrarFuncionarioDto dto) {
        dto.setCpf(dto.getCpf().replaceAll("\\D", ""));
        Funcionario func = funcionarioMapper.toEntity(dto);
        return funcionarioMapper.toDto(funcionarioRepository.save(func));
    }

    public Page<ListarFuncionarioDto> listarFuncionarios(int pagina, int tamanho,
            String nome, Cargo cargo, Boolean status) {
        Specification<Funcionario> spec = Specification.where(FuncionarioSpecification.temNome(nome))
                .and(FuncionarioSpecification.temcargo(cargo))
                .and(FuncionarioSpecification.isAtivo(status));

        Pageable pageable = PageRequest.of(pagina, tamanho);
        return funcionarioRepository.findAll(spec, pageable).map(funcionarioMapper::toDto);
    }

    public ListarFuncionarioDto obterFuncionarioPeloId(long id) {
        Funcionario func = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado"));

        return funcionarioMapper.toDto(func);
    }

    public ListarFuncionarioDto obterFuncionarioPeloCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("\\D", "");
        Funcionario func = funcionarioRepository.findByCpfAndAtivoTrue(cpfLimpo)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado"));

        return funcionarioMapper.toDto(func);
    }

    public ListarFuncionarioDto atualizarFuncionario(Long id, AtualizarFuncionarioDto dto) {
        Funcionario func = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado"));

        if (dto.getNome() != null) {
            func.setNome(dto.getNome());
        }

        if (dto.getEmail() != null) {
            func.setEmail(dto.getEmail());
        }

        if (dto.getTelefone() != null) {
            func.setTelefone(dto.getTelefone());
        }

        if (dto.getSalario() != null) {
            func.setSalario(dto.getSalario());
        }

        if (dto.getCargo() != null) {
            func.setCargo(dto.getCargo());
        }

        return funcionarioMapper.toDto(funcionarioRepository.save(func));
    }

    public void inativarFuncionario(Long id) {
        Funcionario func = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado"));

        func.setAtivo(false);
        funcionarioRepository.save(func);
    }

    public void reativarFuncionario(Long id) {
        Funcionario func = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado"));

        if (func.getAtivo() == true) {
            throw new IllegalStateException("Funcionário já está ativo");
        }

        func.setAtivo(true);
        funcionarioRepository.save(func);
    }
}
