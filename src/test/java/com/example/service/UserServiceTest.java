package com.example.service;

import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;
import com.example.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AsyncService asyncService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
        testUser.setNickname("Test User");
    }

    @Test
    void testRegister_Success() {
        // Given
        when(userService.count(any())).thenReturn(0L);
        when(userService.save(any(User.class))).thenReturn(true);

        // When
        boolean result = userService.register(testUser);

        // Then
        assertTrue(result);
        assertEquals(DigestUtils.md5DigestAsHex("password123".getBytes()), testUser.getPassword());
        verify(asyncService).sendEmailAsync(eq("test@example.com"), anyString(), anyString());
        verify(asyncService).logUserActionAsync(eq(1L), eq("register"), eq("用户注册"));
    }

    @Test
    void testRegister_UsernameExists() {
        // Given
        when(userService.count(any())).thenReturn(1L);

        // When
        boolean result = userService.register(testUser);

        // Then
        assertFalse(result);
        verify(userService, never()).save(any(User.class));
        verify(asyncService, never()).sendEmailAsync(anyString(), anyString(), anyString());
    }

    @Test
    void testLogin_Success() {
        // Given
        String hashedPassword = DigestUtils.md5DigestAsHex("password123".getBytes());
        testUser.setPassword(hashedPassword);
        when(userService.getOne(any())).thenReturn(testUser);
        when(jwtUtil.generateToken(anyString())).thenReturn("test-token");

        // When
        String token = userService.login("testuser", "password123");

        // Then
        assertNotNull(token);
        assertEquals("test-token", token);
        verify(asyncService).logUserActionAsync(eq(1L), eq("login"), eq("用户登录"));
    }

    @Test
    void testLogin_UserNotFound() {
        // Given
        when(userService.getOne(any())).thenReturn(null);

        // When
        String token = userService.login("nonexistent", "password123");

        // Then
        assertNull(token);
        verify(asyncService, never()).logUserActionAsync(anyLong(), anyString(), anyString());
    }

    @Test
    void testLogin_WrongPassword() {
        // Given
        String hashedPassword = DigestUtils.md5DigestAsHex("password123".getBytes());
        testUser.setPassword(hashedPassword);
        when(userService.getOne(any())).thenReturn(testUser);

        // When
        String token = userService.login("testuser", "wrongpassword");

        // Then
        assertNull(token);
        verify(asyncService, never()).logUserActionAsync(anyLong(), anyString(), anyString());
    }

    @Test
    void testFindByUsername_Success() {
        // Given
        when(userService.getOne(any())).thenReturn(testUser);

        // When
        User foundUser = userService.findByUsername("testuser");

        // Then
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testUpdateUserInfo_Success() {
        // Given
        when(userService.updateById(any(User.class))).thenReturn(true);

        // When
        boolean result = userService.updateUserInfo(testUser);

        // Then
        assertTrue(result);
        assertNull(testUser.getPassword()); // 密码应该被设置为null
        verify(asyncService).logUserActionAsync(eq(1L), eq("update_profile"), eq("更新用户信息"));
    }

    @Test
    void testUpdateUserInfo_Failure() {
        // Given
        when(userService.updateById(any(User.class))).thenReturn(false);

        // When
        boolean result = userService.updateUserInfo(testUser);

        // Then
        assertFalse(result);
        verify(asyncService, never()).logUserActionAsync(anyLong(), anyString(), anyString());
    }
} 