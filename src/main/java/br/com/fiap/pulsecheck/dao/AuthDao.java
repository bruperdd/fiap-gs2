package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.model.Users;

public interface AuthDao {

    Users findByEmail(String email);

}
