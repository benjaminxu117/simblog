# SimBlog 快速启动指南

## 🚀 快速开始

### 1. 数据库设置

#### 方法一：使用MySQL命令行
```bash
# 连接到MySQL
mysql -u root -p

# 在MySQL中执行
CREATE DATABASE IF NOT EXISTS simblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE simblog;
SOURCE D:/simblog/src/main/resources/sql/init.sql;
```

#### 方法二：使用MySQL Workbench或其他工具
1. 打开MySQL管理工具
2. 创建数据库 `simblog`
3. 执行 `src/main/resources/sql/init.sql` 脚本

### 2. 修改配置文件

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    username: root  # 你的MySQL用户名
    password: your_password  # 你的MySQL密码
```

### 3. 启动应用

```bash
# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

### 4. 验证启动

启动成功后，你应该看到：
```
✅ 数据库连接成功！
数据库URL: jdbc:mysql://localhost:3306/simblog
数据库产品名称: MySQL
数据库版本: 8.0.x
```

### 5. 测试API

#### 获取文章列表
```bash
curl http://localhost:8080/api/article/list
```

#### 用户登录
```bash
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'
```

## 🔧 常见问题

### 问题1：数据库连接失败
**错误信息**: `Communications link failure`
**解决方案**: 
- 检查MySQL服务是否启动
- 检查端口3306是否可访问
- 检查用户名密码是否正确

### 问题2：数据库不存在
**错误信息**: `Unknown database 'simblog'`
**解决方案**: 执行数据库创建脚本

### 问题3：表不存在
**错误信息**: `Table 'simblog.user' doesn't exist`
**解决方案**: 执行 `init.sql` 脚本创建表结构

## 📋 默认测试数据

系统预置了两个测试账号：
- **管理员**: `admin` / `admin`
- **测试用户**: `test` / `test`

## 🌐 API接口

### 用户接口
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `GET /api/user/profile` - 获取用户信息
- `PUT /api/user/profile` - 更新用户信息

### 文章接口
- `GET /api/article/list` - 获取文章列表
- `GET /api/article/{id}` - 获取文章详情
- `POST /api/article` - 创建文章
- `PUT /api/article/{id}` - 更新文章
- `DELETE /api/article/{id}` - 删除文章

## 📁 项目结构

```
src/main/java/com/example/
├── config/          # 配置类
├── controller/      # 控制器
├── entity/          # 实体类
├── mapper/          # MyBatis Mapper
├── service/         # 服务层
└── util/            # 工具类
```

## 🎯 下一步

1. 完善评论功能
2. 添加点赞功能
3. 实现订阅功能
4. 添加文件上传
5. 实现搜索功能 