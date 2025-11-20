package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;

import java.util.List;

public interface UserService {

    void register(UsersDto dto);
    
    List<Users> listAllUsers();
    
    Users getUserById(int id);
    
    void updateUser(int id, UsersDto dto);
    
    void deactivateUser(int id);
}
