package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.mapper.UserMapper;
import br.com.fiap.pulsecheck.model.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersDaoImpl implements UsersDao {

    private final UserMapper userMapper;

    public UsersDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void createUser(Users user) {
        userMapper.createUser(user);
    }

    @Override
    public List<Users> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Users findById(int id) {
        return userMapper.findById(id);
    }

    @Override
    public Users findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public void updateUser(Users user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deactivateUser(int id) {
        userMapper.deactivateUser(id);
    }

    @Override
    public void activateUser(int id) {
        userMapper.activateUser(id);
    }

}
