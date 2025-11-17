package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.AuthDto;
import br.com.fiap.pulsecheck.dto.JwtDto;
import br.com.fiap.pulsecheck.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Validated @RequestBody AuthDto dto) {
        log.info("New login request: {}", dto.getEmail());
        JwtDto jwt = authService.login(dto);
        return ResponseEntity.ok(jwt);
    }

}
