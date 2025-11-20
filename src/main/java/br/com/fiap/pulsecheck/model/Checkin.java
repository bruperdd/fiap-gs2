package br.com.fiap.pulsecheck.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Checkin {

    private int id;
    private int user_id;
    private int mood;
    private String note;
    private Date date;

}
