package br.com.fiap.pulsecheck.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtDto {

    private String token;
    private String type; // "Bearer"
    private Long expiresIn;

}
