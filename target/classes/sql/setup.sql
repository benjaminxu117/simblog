-- MySQL数据库初始化脚本
-- 请在MySQL命令行或管理工具中执行以下命令

-- 1. 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS simblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 创建用户（可选，如果使用root用户可跳过）
-- CREATE USER 'simblog_user'@'localhost' IDENTIFIED BY 'your_password';
-- GRANT ALL PRIVILEGES ON simblog.* TO 'simblog_user'@'localhost';
-- FLUSH PRIVILEGES;

-- 3. 使用数据库
USE simblog;

-- 4. 检查数据库是否存在
SELECT DATABASE();

-- 5. 显示所有表（执行init.sql后应该能看到表）
SHOW TABLES; 