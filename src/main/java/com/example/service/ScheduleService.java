package com.example.service;

import com.example.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    
    @Autowired
    private AsyncService asyncService;
    
    /**
     * 每天凌晨2点清理过期缓存
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredCache() {
        logger.info("开始执行定时清理过期缓存任务");
        asyncService.cleanExpiredCacheAsync();
    }
    
    /**
     * 每小时同步Redis统计数据到数据库
     */
    @Scheduled(fixedRate = 3600000) // 1小时
    public void syncStatisticsToDatabase() {
        logger.info("开始执行定时同步统计数据任务");
        // 这里可以实现将Redis中的统计数据同步到数据库的逻辑
    }
    
    /**
     * 每5分钟检查系统健康状态
     */
    @Scheduled(fixedRate = 300000) // 5分钟
    public void healthCheck() {
        logger.info("执行系统健康检查");
        // 这里可以实现系统健康检查的逻辑
    }
} 