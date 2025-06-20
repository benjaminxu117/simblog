# 数据库连接设置指南

## 1. 检查MySQL服务状态

### Windows
```bash
# 检查MySQL服务状态
net start | findstr MySQL

# 启动MySQL服务（如果未启动）
net start MySQL80
# 或者
net start MySQL8.0
```

### Linux/Mac
```bash
# 检查MySQL服务状态
sudo systemctl status mysql

# 启动MySQL服务（如果未启动）
sudo systemctl start mysql
```

## 2. 连接到MySQL

```bash
# 使用root用户连接
mysql -u root -p

# 输入密码后，执行以下命令创建数据库
```

## 3. 创建数据库和表

在MySQL命令行中执行：

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS simblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE simblog;

-- 执行完整的初始化脚本
SOURCE /path/to/your/project/src/main/resources/sql/init.sql;
```

## 4. 修改应用配置

编辑 `src/main/resources/application.yml` 文件，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/simblog?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root  # 修改为你的MySQL用户名
    password: your_password  # 修改为你的MySQL密码
```

## 5. 常见问题解决

### 问题1: 连接被拒绝
```
Communications link failure
```
**解决方案**: 检查MySQL服务是否启动，端口3306是否被占用

### 问题2: 认证失败
```
Access denied for user 'root'@'localhost'
```
**解决方案**: 检查用户名和密码是否正确

### 问题3: 数据库不存在
```
Unknown database 'simblog'
```
**解决方案**: 执行数据库创建脚本

### 问题4: 时区问题
```
The server time zone value is unrecognized
```
**解决方案**: 在连接URL中添加 `serverTimezone=Asia/Shanghai`

## 6. 测试连接

启动应用后，查看控制台输出：
- ✅ 数据库连接成功！ - 表示连接正常
- ❌ 数据库连接失败！ - 表示需要检查配置

## 7. 验证数据

启动应用后，可以通过以下API测试：

```bash
# 获取文章列表
curl http://localhost:8080/api/article/list

# 用户登录
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'
```

## 8. 默认测试数据

系统预置了两个测试账号：
- 管理员：`admin` / `admin`
- 测试用户：`test` / `test` 