package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        log.info("Registering user: {}", dto);
        userService.register(dto);
        return ResponseEntity.ok("User created");
    }

    @GetMapping
    public ResponseEntity<List<Users>> listAllUsers() {
        log.info("Listing all users");
        List<Users> users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id) {
        log.info("Getting user by id: {}", id);
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UsersDto dto) {
        log.info("Updating user id: {}", id);
        userService.updateUser(id, dto);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable int id) {
        log.info("Deactivating user id: {}", id);
        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated");
    }

}
