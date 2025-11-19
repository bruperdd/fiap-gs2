package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;

import java.util.List;

public interface UserService {

    void register(UsersDto dto, String emailLogado);
    
    List<Users> listAllUsers(String emailLogado);
    
    Users getUserById(int id, String emailLogado);
    
    void updateUser(int id, UsersDto dto, String emailLogado);
    
    void deactivateUser(int id, String emailLogado);
    
    void activateUser(int id, String emailLogado);
}
