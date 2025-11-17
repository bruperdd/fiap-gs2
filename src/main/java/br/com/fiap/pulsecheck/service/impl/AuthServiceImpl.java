package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.config.JwtProperties;
import br.com.fiap.pulsecheck.dao.AuthDao;
import br.com.fiap.pulsecheck.dto.AuthDto;
import br.com.fiap.pulsecheck.dto.JwtDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.AuthService;
import br.com.fiap.pulsecheck.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthDao authDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final long expiresInMillis;

    public AuthServiceImpl(AuthDao authDao,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           JwtProperties jwtProperties) {
        this.authDao = authDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.expiresInMillis = jwtProperties.getExpiration();
    }

    public JwtDto login(AuthDto dto) {
        Users user = authDao.findByEmail(dto.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword_hash());
        if (!matches) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new JwtDto(token, "Bearer", expiresInMillis);
    }
}
