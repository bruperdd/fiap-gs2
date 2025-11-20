package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.SuggestionDao;
import br.com.fiap.pulsecheck.model.Suggestion;
import br.com.fiap.pulsecheck.service.SuggestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionDao suggestionDao;

    public SuggestionServiceImpl(SuggestionDao suggestionDao) {
        this.suggestionDao = suggestionDao;
    }

    public List<Suggestion> getSuggestionById(int id){
        return suggestionDao.getSuggestionById(id);
    }
}
