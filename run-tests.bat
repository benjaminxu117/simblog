@echo off
chcp 65001
echo ========================================
echo SimBlog 单元测试运行脚本
echo ========================================

echo.
echo 1. 清理并编译项目...
call mvn clean compile

echo.
echo 2. 运行单元测试...
call mvn test

echo.
echo 3. 生成测试报告...
call mvn surefire-report:report

echo.
echo ========================================
echo 测试完成！
echo ========================================
echo.
echo 测试报告位置：
echo - Surefire报告: target/site/surefire-report.html
echo - 测试结果: target/surefire-reports/
echo.
pause 