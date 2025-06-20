-- 创建simblog数据库
CREATE DATABASE IF NOT EXISTS simblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用simblog数据库
USE simblog;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) COMMENT '邮箱',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `avatar` VARCHAR(255) COMMENT '头像',
    `bio` TEXT COMMENT '个人简介',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建文章表
CREATE TABLE IF NOT EXISTS `article` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '文章ID',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `content` LONGTEXT NOT NULL COMMENT '内容',
    `summary` TEXT COMMENT '摘要',
    `author_id` BIGINT NOT NULL COMMENT '作者ID',
    `author_name` VARCHAR(50) COMMENT '作者名称',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览量',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论数',
    `status` VARCHAR(20) NOT NULL DEFAULT 'draft' COMMENT '状态：draft-草稿，published-已发布',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_author_id` (`author_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 插入测试数据
INSERT INTO `user` (`username`, `password`, `email`, `nickname`, `bio`) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@example.com', '管理员', '系统管理员'),
('test', '098f6bcd4621d373cade4e832627b4f6', 'test@example.com', '测试用户', '这是一个测试用户');

INSERT INTO `article` (`title`, `content`, `summary`, `author_id`, `author_name`, `status`) VALUES
('欢迎使用SimBlog', '这是一个简单的博客系统，支持文章的发布、评论、点赞和订阅功能。', 'SimBlog系统介绍', 1, '管理员', 'published'),
('Spring Boot 入门指南', 'Spring Boot是一个基于Spring的快速开发框架...', 'Spring Boot框架介绍', 1, '管理员', 'published');

-- 显示创建的表
SHOW TABLES;

-- 显示用户数据
SELECT * FROM user;

-- 显示文章数据
SELECT * FROM article; 