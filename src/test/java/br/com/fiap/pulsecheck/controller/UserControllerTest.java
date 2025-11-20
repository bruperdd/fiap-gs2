package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.dto.UsersDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.JwtService;
import br.com.fiap.pulsecheck.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    private UsersDto usersDto;
    private Users user;
    private String validToken;

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

        validToken = "Bearer validToken123";
    }

    @Test
    void testRegister_ShouldReturnOk_WhenUserIsCreated() {
        // Arrange
        doNothing().when(userService).register(any(UsersDto.class));

        // Act
        ResponseEntity<String> response = userController.register(usersDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User created", response.getBody());
        verify(userService, times(1)).register(any(UsersDto.class));
    }

    @Test
    void testListAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        when(userService.listAllUsers()).thenReturn(usersList);

        // Act
        ResponseEntity<List<Users>> response = userController.listAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Users> result = response.getBody();
        assertEquals(1, result.size());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testGetUserById_ShouldReturnUser_WhenTokenIsValid() {
        // Arrange
        when(jwtService.extractUser("validToken123")).thenReturn(user);
        when(userService.getUserById(1)).thenReturn(user);

        // Act
        ResponseEntity<Users> response = userController.getUserById(validToken);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Users result = response.getBody();
        assertEquals(1, result.getId());
        verify(jwtService, times(1)).extractUser("validToken123");
        verify(userService, times(1)).getUserById(1);
    }
}

