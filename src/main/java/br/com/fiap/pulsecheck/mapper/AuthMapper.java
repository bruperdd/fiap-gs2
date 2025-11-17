package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {

    Users findByEmail(@Param("email") String email);

}
