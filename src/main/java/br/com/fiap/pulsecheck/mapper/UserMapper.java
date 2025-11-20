package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void createUser (Users user);
    
    List<Users> findAll();
    
    Users findById(int id);
    
    void updateUser(Users user);
    
    void deactivateUser(int id);
    
}
