package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        
        log.info("Registering user: {} - requested by: {}", dto, emailLogado);
        userService.register(dto, emailLogado);
        return ResponseEntity.ok("User created");
    }

    @GetMapping
    public ResponseEntity<List<Users>> listAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        
        log.info("Listing all users - requested by: {}", emailLogado);
        List<Users> users = userService.listAllUsers(emailLogado);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        
        log.info("Getting user by id: {} - requested by: {}", id, emailLogado);
        Users user = userService.getUserById(id, emailLogado);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UsersDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        
        log.info("Updating user id: {} - requested by: {}", id, emailLogado);
        userService.updateUser(id, dto, emailLogado);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        
        log.info("Deactivating user id: {} - requested by: {}", id, emailLogado);
        userService.deactivateUser(id, emailLogado);
        return ResponseEntity.ok("User deactivated");
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        
        log.info("Activating user id: {} - requested by: {}", id, emailLogado);
        userService.activateUser(id, emailLogado);
        return ResponseEntity.ok("User activated");
    }
}
