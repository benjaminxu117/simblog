package com.example.service.impl;

import com.example.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AsyncServiceImpl implements AsyncService {
    
    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Override
    @Async("emailExecutor")
    public void sendEmailAsync(String to, String subject, String content) {
        try {
            logger.info("开始异步发送邮件到: {}", to);
            // 模拟邮件发送过程
            Thread.sleep(2000);
            logger.info("邮件发送成功: {} -> {}", subject, to);
        } catch (Exception e) {
            logger.error("邮件发送失败: {}", e.getMessage(), e);
        }
    }
    
    @Override
    @Async("fileUploadExecutor")
    public void processFileUploadAsync(String fileName, byte[] fileData) {
        try {
            logger.info("开始异步处理文件上传: {}", fileName);
            // 模拟文件处理过程
            Thread.sleep(1000);
            logger.info("文件处理完成: {}", fileName);
        } catch (Exception e) {
            logger.error("文件处理失败: {}", e.getMessage(), e);
        }
    }
    
    @Override
    @Async("taskExecutor")
    public void updateStatisticsAsync(Long articleId, String action) {
        try {
            logger.info("开始异步更新统计数据: 文章{} - 操作{}", articleId, action);
            
            String key = "article:stats:" + articleId;
            switch (action) {
                case "view":
                    redisTemplate.opsForValue().increment(key + ":view");
                    break;
                case "like":
                    redisTemplate.opsForValue().increment(key + ":like");
                    break;
                case "comment":
                    redisTemplate.opsForValue().increment(key + ":comment");
                    break;
            }
            
            // 设置过期时间
            redisTemplate.expire(key + ":" + action, 24, TimeUnit.HOURS);
            
            logger.info("统计数据更新完成: 文章{} - 操作{}", articleId, action);
        } catch (Exception e) {
            logger.error("统计数据更新失败: {}", e.getMessage(), e);
        }
    }
    
    @Override
    @Async("taskExecutor")
    public void logUserActionAsync(Long userId, String action, String details) {
        try {
            logger.info("记录用户行为: 用户{} - 操作{} - 详情{}", userId, action, details);
            // 这里可以将用户行为记录到数据库或日志文件
            // 模拟记录过程
            Thread.sleep(100);
            logger.info("用户行为记录完成: 用户{} - 操作{}", userId, action);
        } catch (Exception e) {
            logger.error("用户行为记录失败: {}", e.getMessage(), e);
        }
    }
    
    @Override
    @Async("taskExecutor")
    public void cleanExpiredCacheAsync() {
        try {
            logger.info("开始异步清理过期缓存");
            // 模拟缓存清理过程
            Thread.sleep(5000);
            logger.info("过期缓存清理完成");
        } catch (Exception e) {
            logger.error("缓存清理失败: {}", e.getMessage(), e);
        }
    }
    
    @Override
    @Async("taskExecutor")
    public void generateArticleSummaryAsync(Long articleId) {
        try {
            logger.info("开始异步生成文章摘要: 文章{}", articleId);
            // 模拟文章摘要生成过程
            Thread.sleep(3000);
            logger.info("文章摘要生成完成: 文章{}", articleId);
        } catch (Exception e) {
            logger.error("文章摘要生成失败: {}", e.getMessage(), e);
        }
    }
} 