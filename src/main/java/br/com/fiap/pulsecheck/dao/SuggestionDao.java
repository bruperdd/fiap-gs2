package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.model.Suggestion;

import java.util.List;

public interface SuggestionDao {

    List<Suggestion> getSuggestionById(int id);
}
