package com.example.service;

public interface AsyncService {
    
    /**
     * 异步发送邮件
     */
    void sendEmailAsync(String to, String subject, String content);
    
    /**
     * 异步处理文件上传
     */
    void processFileUploadAsync(String fileName, byte[] fileData);
    
    /**
     * 异步更新统计数据
     */
    void updateStatisticsAsync(Long articleId, String action);
    
    /**
     * 异步记录用户行为日志
     */
    void logUserActionAsync(Long userId, String action, String details);
    
    /**
     * 异步清理过期缓存
     */
    void cleanExpiredCacheAsync();
    
    /**
     * 异步生成文章摘要
     */
    void generateArticleSummaryAsync(Long articleId);
} 