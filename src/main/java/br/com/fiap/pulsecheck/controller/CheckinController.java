package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.model.Checkin;
import br.com.fiap.pulsecheck.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkins")
@Slf4j
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Validated @RequestBody CheckinDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();

        checkinService.create(dto, "admin@test.com");
        return ResponseEntity.status(201).body("Check-in realizado!");
    }

    @GetMapping("/me")
    public ResponseEntity<List<Checkin>> listMyCheckins() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();
        return ResponseEntity.ok(checkinService.listByAuthenticatedUser("admin@test.com"));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Checkin>> listCheckinsByUserId(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = (String) auth.getPrincipal();

        return ResponseEntity.ok(checkinService.listByUserId(id, "admin@test.com"));
    }
}
