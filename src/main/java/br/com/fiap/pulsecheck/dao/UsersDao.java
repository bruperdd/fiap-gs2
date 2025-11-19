package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.model.Users;

import java.util.List;

public interface UsersDao {

    void createUser(Users user);
    
    List<Users> findAll();
    
    Users findById(int id);
    
    Users findByEmail(String email);
    
    void updateUser(Users user);
    
    void deactivateUser(int id);
    
    void activateUser(int id);
}
