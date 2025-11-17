package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UsersDao usersDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersDao usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }


    public void register(UsersDto dto) {
        Users user = new Users();

        user.setDepartment_id(dto.getDepartment_id());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setCreated_at(new Date());
        user.setActive(Boolean.TRUE);

        String hash = passwordEncoder.encode(dto.getPassword_hash());
        user.setPassword_hash(hash);

        usersDao.createUser(user);
    }

}
