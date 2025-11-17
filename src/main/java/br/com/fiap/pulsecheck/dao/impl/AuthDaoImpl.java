package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.AuthDao;
import br.com.fiap.pulsecheck.mapper.AuthMapper;
import br.com.fiap.pulsecheck.model.Users;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDaoImpl implements AuthDao {

    private final AuthMapper authMapper;

    public AuthDaoImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    public Users findByEmail(String email) {
        return authMapper.findByEmail(email);
    }
}
