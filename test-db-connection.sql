-- 数据库连接测试脚本
-- 请在MySQL命令行中执行以下命令来测试连接

-- 1. 连接到MySQL（请根据你的实际配置修改）
-- mysql -u root -p

-- 2. 检查当前数据库
SELECT DATABASE();

-- 3. 创建simblog数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS simblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 4. 使用simblog数据库
USE simblog;

-- 5. 检查数据库是否存在
SHOW DATABASES LIKE 'simblog';

-- 6. 显示当前数据库中的表（应该为空）
SHOW TABLES;

-- 7. 执行完整的初始化脚本
-- 请将下面的路径替换为你的实际项目路径
-- SOURCE D:/simblog/src/main/resources/sql/init.sql;

-- 8. 再次显示表（应该能看到创建的表）
-- SHOW TABLES;

-- 9. 检查用户表数据
-- SELECT * FROM user;

-- 10. 检查文章表数据
-- SELECT * FROM article; 