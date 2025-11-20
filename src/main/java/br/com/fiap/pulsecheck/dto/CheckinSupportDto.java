package br.com.fiap.pulsecheck.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CheckinSupportDto {

    private Date date;
    private int averageMood;

}
