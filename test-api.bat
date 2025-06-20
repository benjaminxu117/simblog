@echo off
chcp 65001
echo ========================================
echo SimBlog API 测试脚本
echo ========================================

echo.
echo 1. 测试文章列表API...
curl -s http://localhost:8080/api/article/list

echo.
echo.
echo 2. 测试用户登录API...
curl -s -X POST http://localhost:8080/api/user/login -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"admin\"}"

echo.
echo.
echo ========================================
echo API测试完成
echo ========================================
pause 