package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.model.Suggestion;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.JwtService;
import br.com.fiap.pulsecheck.service.SuggestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestions")
@Slf4j
public class SuggestionController {

    private final SuggestionService suggestionService;

    private final JwtService jwtService;

    public SuggestionController(SuggestionService suggestionService, JwtService jwtService) {
        this.suggestionService = suggestionService;
        this.jwtService = jwtService;
    }

    @GetMapping("/getSuggestionForUser")
    public ResponseEntity<List<Suggestion>> getSuggestionForUser(@RequestHeader("Authorization") String header) {
        Users user = retrieveUserData(header);
        log.info("Getting suggestions for mood for user id: {}", user.getId());
        List<Suggestion> suggestion = suggestionService.getSuggestionForUser(user.getId());
        return ResponseEntity.ok(suggestion);
    }

    private Users retrieveUserData(String header) {
        String token = header.replace("Bearer ", "");
        return jwtService.extractUser(token);
    }

}
