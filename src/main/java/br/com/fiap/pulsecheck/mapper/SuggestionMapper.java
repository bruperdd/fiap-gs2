package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Suggestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SuggestionMapper {

    List<Suggestion> getSuggestionById(@Param("id") int id);

}
