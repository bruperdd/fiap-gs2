package br.com.fiap.pulsecheck.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Users {

    private int id;
    private int department_id;
    private String name;
    private String email;
    private String role;
    private String password_hash;
    private Date created_at;
    private Boolean active;

}
