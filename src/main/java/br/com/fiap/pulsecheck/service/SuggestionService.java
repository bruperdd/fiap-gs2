package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.model.Suggestion;

import java.util.List;

public interface SuggestionService {

    List<Suggestion> getSuggestionForUser(int id);
}
