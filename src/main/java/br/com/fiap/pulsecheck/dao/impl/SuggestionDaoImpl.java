package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.SuggestionDao;
import br.com.fiap.pulsecheck.mapper.SuggestionMapper;
import br.com.fiap.pulsecheck.model.Suggestion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SuggestionDaoImpl implements SuggestionDao {

    private final SuggestionMapper suggestionMapper;

    public SuggestionDaoImpl(SuggestionMapper suggestionMapper) {
        this.suggestionMapper = suggestionMapper;
    }

    public List<Suggestion> getSuggestionById(int id){
        return suggestionMapper.getSuggestionById(id);
    }

}
