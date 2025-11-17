package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> register(@Validated @RequestBody UsersDto dto) {
        log.info("Registering user: {}", dto);
        userService.register(dto);
        return ResponseEntity.ok("User created");
    }
}
