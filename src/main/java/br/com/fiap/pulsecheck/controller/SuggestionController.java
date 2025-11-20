package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.model.Suggestion;
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

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping()
    public ResponseEntity<List<Suggestion>> register(@Validated @RequestParam int id) {
        log.info("Getting suggestions for mood id: {}", id);
        List<Suggestion> suggestion = suggestionService.getSuggestionById(id);
        return ResponseEntity.ok(suggestion);
    }

}
