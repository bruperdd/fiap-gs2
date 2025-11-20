package br.com.fiap.pulsecheck.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CheckinDto {

    private int id;
    private int mood;
    private String note;
    private Date date;

}
