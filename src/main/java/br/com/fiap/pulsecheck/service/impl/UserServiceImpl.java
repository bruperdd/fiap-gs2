package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<Users> listAllUsers() {
        return usersDao.findAll();
    }

    @Override
    public Users getUserById(int id) {
        Users user = usersDao.findById(id);
        
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        return user;
    }

    @Override
    public void updateUser(int id, UsersDto dto) {
        Users userToUpdate = usersDao.findById(id);
        
        if (userToUpdate == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        if (dto.getName() != null) {
            userToUpdate.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            userToUpdate.setEmail(dto.getEmail());
        }
        
        if (dto.getRole() != null) {
            userToUpdate.setRole(dto.getRole());
        }
        if (dto.getDepartment_id() > 0) {
            userToUpdate.setDepartment_id(dto.getDepartment_id());
        }
        
        usersDao.updateUser(userToUpdate);
    }

    @Override
    public void deactivateUser(int id) {
        Users userToDeactivate = usersDao.findById(id);
        
        if (userToDeactivate == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        usersDao.deactivateUser(id);
    }

}
