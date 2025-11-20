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


    public void register(UsersDto dto, String emailLogado) {
        // Apenas admin pode criar usuários
//        Users userLogado = usersDao.findByEmail(emailLogado);
        
//        if (userLogado == null) {
//            throw new RuntimeException("Usuário não encontrado");
//        }
//
//        if (!"admin".equals(userLogado.getRole())) {
//            throw new RuntimeException("Apenas administradores podem criar usuários");
//        }
        
        Users user = new Users();

        user.setDepartment_id(dto.getDepartment_id());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setCreated_at(new Date());
        user.setActive(Boolean.TRUE);

        String hash = passwordEncoder.encode(dto.getPassword());
        user.setPassword_hash(hash);

        usersDao.createUser(user);
    }

    @Override
    public List<Users> listAllUsers(String emailLogado) {
        Users userLogado = usersDao.findByEmail(emailLogado);
        
        if (userLogado == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        if (!"admin".equals(userLogado.getRole())) {
            throw new RuntimeException("Apenas administradores podem listar todos os usuários");
        }
        
        return usersDao.findAll();
    }

    @Override
    public Users getUserById(int id, String emailLogado) {
        Users user = usersDao.findById(id);
        
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        Users userLogado = usersDao.findByEmail(emailLogado);
        
        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }
        
        // Admin pode ver qualquer perfil, user só pode ver próprio
        if (!"admin".equals(userLogado.getRole()) && userLogado.getId() != id) {
            throw new RuntimeException("Você só pode ver seu próprio perfil");
        }
        
        return user;
    }

    @Override
    public void updateUser(int id, UsersDto dto, String emailLogado) {
        Users userToUpdate = usersDao.findById(id);
        
        if (userToUpdate == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        Users userLogado = usersDao.findByEmail(emailLogado);
        
        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }
        
        boolean isAdmin = "admin".equals(userLogado.getRole());
        boolean isOwnProfile = userLogado.getId() == id;
        
        // User só pode atualizar próprio perfil
        if (!isAdmin && !isOwnProfile) {
            throw new RuntimeException("Você só pode atualizar seu próprio perfil");
        }
        
        // User não pode alterar role ou departamento
        if (!isAdmin) {
            if (dto.getRole() != null) {
                throw new RuntimeException("Você não pode alterar sua role");
            }
            if (dto.getDepartment_id() > 0) {
                throw new RuntimeException("Você não pode alterar seu departamento");
            }
        }
        
        // Atualizar campos permitidos
        if (dto.getName() != null) {
            userToUpdate.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            userToUpdate.setEmail(dto.getEmail());
        }
        
        // Apenas admin pode alterar role e departamento
        if (isAdmin) {
            if (dto.getRole() != null) {
                userToUpdate.setRole(dto.getRole());
            }
            if (dto.getDepartment_id() > 0) {
                userToUpdate.setDepartment_id(dto.getDepartment_id());
            }
        }
        
        usersDao.updateUser(userToUpdate);
    }

    @Override
    public void deactivateUser(int id, String emailLogado) {
        Users userToDeactivate = usersDao.findById(id);
        
        if (userToDeactivate == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        Users userLogado = usersDao.findByEmail(emailLogado);
        
        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }
        
        boolean isAdmin = "admin".equals(userLogado.getRole());
        boolean isOwnProfile = userLogado.getId() == id;
        
        // Admin pode desativar qualquer usuário, user só pode desativar próprio
        if (!isAdmin && !isOwnProfile) {
            throw new RuntimeException("Você só pode desativar sua própria conta");
        }
        
        usersDao.deactivateUser(id);
    }

    @Override
    public void activateUser(int id, String emailLogado) {
        Users userToActivate = usersDao.findById(id);
        
        if (userToActivate == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        Users userLogado = usersDao.findByEmail(emailLogado);
        
        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }
        
        // Apenas admin pode ativar usuários
        if (!"admin".equals(userLogado.getRole())) {
            throw new RuntimeException("Apenas administradores podem ativar usuários");
        }
        
        usersDao.activateUser(id);
    }

}
