package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.mapper.UserMapper;
import br.com.fiap.pulsecheck.model.Users;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDaoImpl implements UsersDao {

    private final UserMapper userMapper;

    public UsersDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void createUser(Users user) {
        userMapper.createUser(user);
    }

}
