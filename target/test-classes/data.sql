-- 测试数据初始化脚本

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
);

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
);

-- 插入测试用户数据
INSERT INTO `user` (`username`, `password`, `email`, `nickname`, `bio`) VALUES
('testuser', '21232f297a57a5a743894a0e4a801fc3', 'test@example.com', '测试用户', '这是一个测试用户'),
('testadmin', '21232f297a57a5a743894a0e4a801fc3', 'admin@example.com', '测试管理员', '这是一个测试管理员');

-- 插入测试文章数据
INSERT INTO `article` (`title`, `content`, `summary`, `author_id`, `author_name`, `status`) VALUES
('测试文章1', '这是第一篇测试文章的内容。', '测试文章1摘要', 1, '测试用户', 'published'),
('测试文章2', '这是第二篇测试文章的内容。', '测试文章2摘要', 1, '测试用户', 'published'),
('草稿文章', '这是一篇草稿文章。', '草稿文章摘要', 2, '测试管理员', 'draft'); 