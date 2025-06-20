package com.example.service;

import com.example.service.impl.AsyncServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private AsyncServiceImpl asyncService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testSendEmailAsync() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        // When
        asyncService.sendEmailAsync(to, subject, content);

        // Then
        // 由于是异步方法，我们主要验证方法能够正常执行而不抛出异常
        // 实际的异步执行验证需要在集成测试中进行
    }

    @Test
    void testProcessFileUploadAsync() {
        // Given
        String fileName = "test.jpg";
        byte[] fileData = "test file content".getBytes();

        // When
        asyncService.processFileUploadAsync(fileName, fileData);

        // Then
        // 验证方法能够正常执行
    }

    @Test
    void testUpdateStatisticsAsync_View() {
        // Given
        Long articleId = 1L;
        String action = "view";

        // When
        asyncService.updateStatisticsAsync(articleId, action);

        // Then
        verify(valueOperations).increment("article:stats:1:view");
        verify(redisTemplate).expire("article:stats:1:view", 24, java.util.concurrent.TimeUnit.HOURS);
    }

    @Test
    void testUpdateStatisticsAsync_Like() {
        // Given
        Long articleId = 1L;
        String action = "like";

        // When
        asyncService.updateStatisticsAsync(articleId, action);

        // Then
        verify(valueOperations).increment("article:stats:1:like");
        verify(redisTemplate).expire("article:stats:1:like", 24, java.util.concurrent.TimeUnit.HOURS);
    }

    @Test
    void testUpdateStatisticsAsync_Comment() {
        // Given
        Long articleId = 1L;
        String action = "comment";

        // When
        asyncService.updateStatisticsAsync(articleId, action);

        // Then
        verify(valueOperations).increment("article:stats:1:comment");
        verify(redisTemplate).expire("article:stats:1:comment", 24, java.util.concurrent.TimeUnit.HOURS);
    }

    @Test
    void testUpdateStatisticsAsync_UnknownAction() {
        // Given
        Long articleId = 1L;
        String action = "unknown";

        // When
        asyncService.updateStatisticsAsync(articleId, action);

        // Then
        verify(valueOperations, never()).increment(anyString());
        verify(redisTemplate, never()).expire(anyString(), anyLong(), any());
    }

    @Test
    void testLogUserActionAsync() {
        // Given
        Long userId = 1L;
        String action = "login";
        String details = "用户登录";

        // When
        asyncService.logUserActionAsync(userId, action, details);

        // Then
        // 验证方法能够正常执行
    }

    @Test
    void testCleanExpiredCacheAsync() {
        // When
        asyncService.cleanExpiredCacheAsync();

        // Then
        // 验证方法能够正常执行
    }

    @Test
    void testGenerateArticleSummaryAsync() {
        // Given
        Long articleId = 1L;

        // When
        asyncService.generateArticleSummaryAsync(articleId);

        // Then
        // 验证方法能够正常执行
    }
} 