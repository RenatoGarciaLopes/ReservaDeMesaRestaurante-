package com.example.demo.controller;

import com.example.demo.dto.FuncionarioDto.LoginFuncionarioDto;
import com.example.demo.service.FuncionarioService;
import com.example.demo.service.Utils.JwtUtil;
import com.example.demo.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/funcionario")
public class FuncionarioAuthController {

    @Autowired
    private FuncionarioService funcionarioService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginFuncionarioDto loginDto) {
        try {
            Funcionario funcionario = funcionarioService.autenticarFuncionario(loginDto.getEmail(), loginDto.getSenha());
            String token = jwtUtil.generateToken(funcionario.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("erro", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
} 