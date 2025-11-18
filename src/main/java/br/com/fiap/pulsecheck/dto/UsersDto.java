package br.com.fiap.pulsecheck.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDto {

    private int department_id;
    private String name;
    private String email;
    private String role;
    private String password;
}
