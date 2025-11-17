package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.dto.AuthDto;
import br.com.fiap.pulsecheck.dto.JwtDto;

public interface AuthService {

    JwtDto login(AuthDto Dto);
}
