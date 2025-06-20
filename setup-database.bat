@echo off
chcp 65001
echo ========================================
echo SimBlog 数据库设置脚本
echo ========================================

echo.
echo 1. 检查MySQL服务状态...
net start | findstr MySQL

echo.
echo 2. 尝试连接MySQL并创建数据库...

REM 尝试使用默认MySQL路径
set MYSQL_PATHS=C:\Program Files\MySQL\MySQL Server 8.0\bin;C:\Program Files\MySQL\MySQL Server 5.7\bin;C:\xampp\mysql\bin;C:\wamp\bin\mysql\mysql5.7.36\bin

for %%p in (%MYSQL_PATHS%) do (
    if exist "%%p\mysql.exe" (
        echo 找到MySQL在: %%p
        echo 正在创建数据库和表...
        
        REM 创建数据库
        "%%p\mysql.exe" -u root -ppassword -e "CREATE DATABASE IF NOT EXISTS simblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>nul
        if !errorlevel! equ 0 (
            echo 数据库创建成功！
            
            REM 创建用户表
            "%%p\mysql.exe" -u root -ppassword simblog -e "CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL AUTO_INCREMENT, username VARCHAR(50) NOT NULL, password VARCHAR(100) NOT NULL, email VARCHAR(100), nickname VARCHAR(50), avatar VARCHAR(255), bio TEXT, create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, deleted TINYINT NOT NULL DEFAULT 0, PRIMARY KEY (id), UNIQUE KEY uk_username (username)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;" 2>nul
            
            REM 创建文章表
            "%%p\mysql.exe" -u root -ppassword simblog -e "CREATE TABLE IF NOT EXISTS article (id BIGINT NOT NULL AUTO_INCREMENT, title VARCHAR(200) NOT NULL, content LONGTEXT NOT NULL, summary TEXT, author_id BIGINT NOT NULL, author_name VARCHAR(50), view_count INT NOT NULL DEFAULT 0, like_count INT NOT NULL DEFAULT 0, comment_count INT NOT NULL DEFAULT 0, status VARCHAR(20) NOT NULL DEFAULT 'draft', create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, deleted TINYINT NOT NULL DEFAULT 0, PRIMARY KEY (id), KEY idx_author_id (author_id), KEY idx_status (status), KEY idx_create_time (create_time)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;" 2>nul
            
            REM 插入测试数据
            "%%p\mysql.exe" -u root -ppassword simblog -e "INSERT INTO user (username, password, email, nickname, bio) VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@example.com', '管理员', '系统管理员'), ('test', '098f6bcd4621d373cade4e832627b4f6', 'test@example.com', '测试用户', '这是一个测试用户');" 2>nul
            
            "%%p\mysql.exe" -u root -ppassword simblog -e "INSERT INTO article (title, content, summary, author_id, author_name, status) VALUES ('欢迎使用SimBlog', '这是一个简单的博客系统，支持文章的发布、评论、点赞和订阅功能。', 'SimBlog系统介绍', 1, '管理员', 'published'), ('Spring Boot 入门指南', 'Spring Boot是一个基于Spring的快速开发框架...', 'Spring Boot框架介绍', 1, '管理员', 'published');" 2>nul
            
            echo 数据库和表创建成功！
            echo.
            echo 3. 验证数据...
            "%%p\mysql.exe" -u root -ppassword simblog -e "SHOW TABLES; SELECT COUNT(*) as user_count FROM user; SELECT COUNT(*) as article_count FROM article;" 2>nul
            
            echo.
            echo ========================================
            echo 数据库设置完成！
            echo ========================================
            echo 现在可以启动Spring Boot应用了
            echo.
            pause
            goto :end
        )
    )
)

echo.
echo ========================================
echo 数据库设置失败！
echo ========================================
echo 可能的原因：
echo 1. MySQL未安装或未添加到PATH
echo 2. MySQL服务未启动
echo 3. 用户名或密码错误
echo.
echo 解决方案：
echo 1. 检查MySQL是否已安装
echo 2. 启动MySQL服务：net start MySQL80
echo 3. 检查root密码是否为password
echo.
pause

:end 