package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Article;
import com.example.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    private AsyncService asyncService;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article testArticle;
    private Page<Article> testPage;

    @BeforeEach
    void setUp() {
        testArticle = new Article();
        testArticle.setId(1L);
        testArticle.setTitle("Test Article");
        testArticle.setContent("This is a test article content");
        testArticle.setAuthorId(1L);
        testArticle.setAuthorName("Test Author");
        testArticle.setStatus("published");

        testPage = new Page<>(1, 10);
    }

    @Test
    void testGetArticlePage_Success() {
        // Given
        List<Article> articles = Arrays.asList(testArticle);
        testPage.setRecords(articles);
        testPage.setTotal(1);
        
        when(articleService.page(any(), any())).thenReturn(testPage);

        // When
        IPage<Article> result = articleService.getArticlePage(testPage, "published");

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRecords().size());
        assertEquals("Test Article", result.getRecords().get(0).getTitle());
    }

    @Test
    void testGetArticlePage_WithNullStatus() {
        // Given
        List<Article> articles = Arrays.asList(testArticle);
        testPage.setRecords(articles);
        testPage.setTotal(1);
        
        when(articleService.page(any(), any())).thenReturn(testPage);

        // When
        IPage<Article> result = articleService.getArticlePage(testPage, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotal());
    }

    @Test
    void testGetArticleDetail_Success() {
        // Given
        when(articleService.getById(1L)).thenReturn(testArticle);

        // When
        Article result = articleService.getArticleDetail(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Article", result.getTitle());
        assertEquals("Test Author", result.getAuthorName());
    }

    @Test
    void testGetArticleDetail_NotFound() {
        // Given
        when(articleService.getById(999L)).thenReturn(null);

        // When
        Article result = articleService.getArticleDetail(999L);

        // Then
        assertNull(result);
    }

    @Test
    void testCreateArticle_Success() {
        // Given
        when(articleService.save(any(Article.class))).thenReturn(true);

        // When
        boolean result = articleService.createArticle(testArticle);

        // Then
        assertTrue(result);
        assertEquals(0, testArticle.getViewCount());
        assertEquals(0, testArticle.getLikeCount());
        assertEquals(0, testArticle.getCommentCount());
        verify(asyncService).generateArticleSummaryAsync(1L);
    }

    @Test
    void testCreateArticle_Failure() {
        // Given
        when(articleService.save(any(Article.class))).thenReturn(false);

        // When
        boolean result = articleService.createArticle(testArticle);

        // Then
        assertFalse(result);
        verify(asyncService, never()).generateArticleSummaryAsync(anyLong());
    }

    @Test
    void testUpdateArticle_Success() {
        // Given
        when(articleService.updateById(any(Article.class))).thenReturn(true);

        // When
        boolean result = articleService.updateArticle(testArticle);

        // Then
        assertTrue(result);
    }

    @Test
    void testUpdateArticle_Failure() {
        // Given
        when(articleService.updateById(any(Article.class))).thenReturn(false);

        // When
        boolean result = articleService.updateArticle(testArticle);

        // Then
        assertFalse(result);
    }

    @Test
    void testDeleteArticle_Success() {
        // Given
        when(articleService.removeById(1L)).thenReturn(true);

        // When
        boolean result = articleService.deleteArticle(1L);

        // Then
        assertTrue(result);
    }

    @Test
    void testDeleteArticle_Failure() {
        // Given
        when(articleService.removeById(1L)).thenReturn(false);

        // When
        boolean result = articleService.deleteArticle(1L);

        // Then
        assertFalse(result);
    }

    @Test
    void testIncrementViewCount_Success() {
        // Given
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.increment(anyString())).thenReturn(1L);

        // When
        articleService.incrementViewCount(1L);

        // Then
        verify(redisTemplate.opsForValue()).increment("article:view:1");
        verify(redisTemplate).expire("article:view:1", 1, java.util.concurrent.TimeUnit.HOURS);
        verify(asyncService).updateStatisticsAsync(1L, "view");
    }
} 