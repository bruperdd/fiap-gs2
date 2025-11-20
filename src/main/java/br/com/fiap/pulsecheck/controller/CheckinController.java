package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.dto.CheckinStatsDto;
import br.com.fiap.pulsecheck.model.Checkin;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.CheckinService;
import br.com.fiap.pulsecheck.service.JwtService;
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

    private final JwtService jwtService;

    public CheckinController(CheckinService checkinService, JwtService jwtService) {
        this.checkinService = checkinService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestHeader("Authorization") String header, @Validated @RequestBody CheckinDto dto) {
        Users user = retrieveUserData(header);
        checkinService.create(dto, user.getId());
        return ResponseEntity.ok("Check-in realizado!");
    }

    @GetMapping("/listMyCheckins")
    public ResponseEntity<List<CheckinDto>> listMyCheckins(@RequestHeader("Authorization") String header) {
        Users users = retrieveUserData(header);
        List<CheckinDto> checkins = checkinService.listMyCheckins(users.getId());
        return ResponseEntity.ok(checkins);
    }

    @GetMapping("/getCheckinStatus")
    public ResponseEntity<CheckinStatsDto> getCheckinStatus(@RequestHeader("Authorization") String header) {
        Users users = retrieveUserData(header);
        CheckinStatsDto checkins = checkinService.getCheckinStatus(users.getId());
        return ResponseEntity.ok(checkins);
    }

    private Users retrieveUserData(String header) {
        String token = header.replace("Bearer ", "");
        return jwtService.extractUser(token);
    }
}
