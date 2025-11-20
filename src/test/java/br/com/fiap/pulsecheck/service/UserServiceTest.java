package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsersDao usersDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UsersDto usersDto;
    private Users user;

    @BeforeEach
    void setUp() {
        usersDto = new UsersDto();
        usersDto.setEmail("test@test.com");
        usersDto.setPassword("password123");
        usersDto.setName("Test User");
        usersDto.setRole("user");
        usersDto.setDepartment_id(1);

        user = new Users();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setName("Test User");
        user.setRole("user");
        user.setDepartment_id(1);
        user.setActive(true);
        user.setCreated_at(new Date());
    }

    @Test
    void testRegister_ShouldCreateUserSuccessfully() {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doNothing().when(usersDao).createUser(any(Users.class));

        // Act
        userService.register(usersDto);

        // Assert
        verify(passwordEncoder, times(1)).encode("password123");
        verify(usersDao, times(1)).createUser(any(Users.class));
    }

    @Test
    void testGetUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        int userId = 1;
        when(usersDao.findById(userId)).thenReturn(user);

        // Act
        Users result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("test@test.com", result.getEmail());
        verify(usersDao, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        int userId = 999;
        when(usersDao.findById(userId)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usersDao, times(1)).findById(userId);
    }

    @Test
    void testUpdateUser_ShouldUpdateUserSuccessfully() {
        // Arrange
        int userId = 1;
        UsersDto updateDto = new UsersDto();
        updateDto.setName("Updated Name");
        updateDto.setEmail("updated@test.com");

        when(usersDao.findById(userId)).thenReturn(user);
        doNothing().when(usersDao).updateUser(any(Users.class));

        // Act
        userService.updateUser(userId, updateDto);

        // Assert
        verify(usersDao, times(1)).findById(userId);
        verify(usersDao, times(1)).updateUser(any(Users.class));
    }

    @Test
    void testListAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        when(usersDao.findAll()).thenReturn(usersList);

        // Act
        List<Users> result = userService.listAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usersDao, times(1)).findAll();
    }
}

