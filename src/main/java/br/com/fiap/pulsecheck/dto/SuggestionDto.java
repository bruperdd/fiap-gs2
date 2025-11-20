package br.com.fiap.pulsecheck.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionDto {

    private String title;
    private String description;
    private int mood_target;
}
