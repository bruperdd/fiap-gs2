package br.com.fiap.pulsecheck.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Suggestion {

    private int id;
    private String title;
    private String description;
    private int mood_target;

}
