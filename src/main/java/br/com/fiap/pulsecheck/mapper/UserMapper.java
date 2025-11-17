package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void createUser (Users user);
}
