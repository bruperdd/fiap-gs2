package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.JwtService;
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

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/getUserById")
    public ResponseEntity<Users> getUserById(@RequestHeader("Authorization") String header) {
        Users userInfo = retrieveUserData(header);
        log.info("Getting user by id: {}", userInfo.getId());
        Users user = userService.getUserById(userInfo.getId());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/listAllUsers")
    public ResponseEntity<List<Users>> listAllUsers() {
        log.info("Listing all users");
        List<Users> users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody UsersDto dto) {
        log.info("Registering user: {}", dto);
        userService.register(dto);
        return ResponseEntity.ok("User created");
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String header, @RequestBody UsersDto dto) {
        Users userInfo = retrieveUserData(header);
        log.info("Updating user id: {}", userInfo.getId());
        userService.updateUser(userInfo.getId(), dto);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/deactivateUser")
    public ResponseEntity<String> deactivateUser(@RequestHeader("Authorization") String header) {
        Users userInfo = retrieveUserData(header);
        log.info("Deactivating user id: {}", userInfo.getId());
        userService.deactivateUser(userInfo.getId());
        return ResponseEntity.ok("User deactivated");
    }

    private Users retrieveUserData(String header) {
        String token = header.replace("Bearer ", "");
        return jwtService.extractUser(token);
    }

}
