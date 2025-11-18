package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Suggestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SuggestionMapper {

    Suggestion getSuggestionById(@Param("id") int id);

}
