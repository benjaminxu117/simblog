package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseConfig implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            log.info("✅ 数据库连接成功！");
            log.info("数据库URL: {}", connection.getMetaData().getURL());
            log.info("数据库产品名称: {}", connection.getMetaData().getDatabaseProductName());
            log.info("数据库版本: {}", connection.getMetaData().getDatabaseProductVersion());
        } catch (Exception e) {
            log.error("❌ 数据库连接失败！", e);
            log.error("请检查以下配置：");
            log.error("1. MySQL服务是否启动");
            log.error("2. 数据库用户名和密码是否正确");
            log.error("3. 数据库simblog是否存在");
            log.error("4. 端口3306是否可访问");
        }
    }
} 