package br.com.fiap.pulsecheck.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Department {
    private int department_id;
    private String name;
    private Date created_at;
}
