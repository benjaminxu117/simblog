@echo off
chcp 65001
echo ========================================
echo SimBlog 异步功能测试脚本
echo ========================================

echo.
echo 等待应用启动...
timeout /t 10 /nobreak >nul

echo.
echo 1. 测试用户注册（包含异步邮件发送）...
curl -s -X POST http://localhost:8080/api/user/register -H "Content-Type: application/json" -d "{\"username\":\"async_test\",\"password\":\"123456\",\"email\":\"async_test@example.com\",\"nickname\":\"异步测试用户\"}"

echo.
echo.
echo 2. 测试用户登录（包含异步行为日志）...
curl -s -X POST http://localhost:8080/api/user/login -H "Content-Type: application/json" -d "{\"username\":\"async_test\",\"password\":\"123456\"}"

echo.
echo.
echo 3. 测试文章查看（包含异步统计数据更新）...
curl -s http://localhost:8080/api/article/1

echo.
echo.
echo 4. 测试文件上传API（异步文件处理）...
echo 注意：这是一个模拟的文件上传测试
curl -s -X POST http://localhost:8080/api/file/upload -F "file=@pom.xml"

echo.
echo.
echo ========================================
echo 异步功能测试完成
echo ========================================
echo.
echo 请查看应用日志以观察异步任务的执行情况：
echo - 邮件发送任务
echo - 用户行为日志记录
echo - 统计数据更新
echo - 文件处理任务
echo - 定时任务执行
echo.
pause 