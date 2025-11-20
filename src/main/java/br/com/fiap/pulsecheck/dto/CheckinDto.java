package br.com.fiap.pulsecheck.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckinDto {

    private int user_id;
    private int mood;
    private String note;

}
