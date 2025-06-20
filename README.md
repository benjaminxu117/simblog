# SimBlog - 简单博客系统

一个基于Spring Boot + MyBatis Plus + Redis + MySQL的简单博客后端系统。

## 功能特性

- 用户注册、登录、个人信息管理
- 文章的发布、编辑、删除、查看
- 文章评论功能
- 文章点赞功能
- 用户订阅功能
- JWT身份认证
- Redis缓存支持

## 技术栈

- **后端框架**: Spring Boot 2.7.0
- **ORM框架**: MyBatis Plus 3.5.2
- **数据库**: MySQL 8.0+
- **缓存**: Redis 6.0+
- **身份认证**: JWT
- **构建工具**: Maven

## 环境要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或更高版本
- Redis 6.0 或更高版本

## 快速开始

### 1. 数据库配置

1. 创建MySQL数据库
2. 执行 `src/main/resources/sql/init.sql` 脚本创建表结构和测试数据

### 2. 配置文件

修改 `src/main/resources/application.yml` 中的数据库和Redis配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/simblog?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
```

### 3. 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

项目将在 `http://localhost:8080` 启动

## API接口

### 用户相关

- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `GET /api/user/profile` - 获取用户信息
- `PUT /api/user/profile` - 更新用户信息

### 文章相关

- `GET /api/article/list` - 获取文章列表（支持分页）
- `GET /api/article/{id}` - 获取文章详情
- `POST /api/article` - 创建文章
- `PUT /api/article/{id}` - 更新文章
- `DELETE /api/article/{id}` - 删除文章

## 项目结构

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器
│   │   ├── entity/          # 实体类
│   │   ├── mapper/          # MyBatis Mapper
│   │   ├── service/         # 服务层
│   │   │   └── impl/        # 服务实现
│   │   └── util/            # 工具类
│   └── resources/
│       ├── sql/             # SQL脚本
│       └── application.yml  # 配置文件
└── test/                    # 测试代码
```

## 测试账号

系统预置了两个测试账号：

- 管理员账号：`admin` / `admin`
- 测试账号：`test` / `test`

## 开发说明

### 添加新功能

1. 在 `entity` 包中创建实体类
2. 在 `mapper` 包中创建Mapper接口
3. 在 `service` 包中创建服务接口和实现
4. 在 `controller` 包中创建控制器

### 数据库设计

- 使用逻辑删除（`deleted` 字段）
- 自动填充创建时间和更新时间
- 使用MyBatis Plus的注解进行ORM映射

### 缓存策略

- 文章浏览量使用Redis缓存
- 用户会话使用JWT Token
- 可根据需要扩展更多缓存功能

## 部署

### 打包

```bash
mvn clean package
```

### 运行

```bash
java -jar target/simblog-1.0-SNAPSHOT.jar
```

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 许可证

MIT License
