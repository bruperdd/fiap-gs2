package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.config.JwtProperties;
import br.com.fiap.pulsecheck.dao.AuthDao;
import br.com.fiap.pulsecheck.dto.AuthDto;
import br.com.fiap.pulsecheck.dto.JwtDto;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthDao authDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthDto authDto;
    private Users user;

    @BeforeEach
    void setUp() {
        // Configurar mock antes de criar o serviço (é usado no construtor)
        lenient().when(jwtProperties.getExpiration()).thenReturn(3600000L);
        
        authDto = new AuthDto();
        authDto.setEmail("test@test.com");
        authDto.setPassword("password123");

        user = new Users();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");
        user.setRole("user");
        user.setDepartment_id(1);
    }

    @Test
    void testLogin_ShouldReturnJwtToken_WhenCredentialsAreValid() {
        // Arrange
        String expectedToken = "mockJwtToken";
        when(authDao.findByEmail("test@test.com")).thenReturn(user);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(any(Users.class))).thenReturn(expectedToken);

        // Act
        JwtDto result = authService.login(authDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken, result.getToken());
        assertEquals("Bearer", result.getType());
        verify(authDao, times(1)).findByEmail("test@test.com");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
        verify(jwtService, times(1)).generateToken(any(Users.class));
    }

    @Test
    void testLogin_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(authDao.findByEmail("test@test.com")).thenReturn(null);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.login(authDto);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Invalid credentials", exception.getReason());
        verify(authDao, times(1)).findByEmail("test@test.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testLogin_ShouldThrowException_WhenPasswordIsInvalid() {
        // Arrange
        when(authDao.findByEmail("test@test.com")).thenReturn(user);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.login(authDto);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Invalid credentials", exception.getReason());
        verify(authDao, times(1)).findByEmail("test@test.com");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
        verify(jwtService, never()).generateToken(any(Users.class));
    }
}

