@echo off
chcp 65001
echo ========================================
echo MySQL连接测试脚本
echo ========================================

echo.
echo 1. 检查MySQL服务状态...
net start | findstr MySQL

echo.
echo 2. 尝试连接MySQL（无密码）...
echo 如果成功，你会看到MySQL版本信息
echo 如果失败，请检查你的MySQL配置
echo.

REM 尝试使用默认MySQL路径
set MYSQL_PATHS=C:\Program Files\MySQL\MySQL Server 8.0\bin;C:\Program Files\MySQL\MySQL Server 5.7\bin;C:\xampp\mysql\bin;C:\wamp\bin\mysql\mysql5.7.36\bin

for %%p in (%MYSQL_PATHS%) do (
    if exist "%%p\mysql.exe" (
        echo 找到MySQL在: %%p
        "%%p\mysql.exe" -u root -e "SELECT VERSION();" 2>nul
        if !errorlevel! equ 0 (
            echo MySQL连接成功！
            goto :success
        )
    )
)

echo.
echo 3. 尝试连接MySQL（有密码）...
echo 请输入你的MySQL root密码（如果不知道密码，请按回车跳过）:
set /p MYSQL_PASSWORD=

if not "%MYSQL_PASSWORD%"=="" (
    for %%p in (%MYSQL_PATHS%) do (
        if exist "%%p\mysql.exe" (
            echo 尝试使用密码连接...
            "%%p\mysql.exe" -u root -p%MYSQL_PASSWORD% -e "SELECT VERSION();" 2>nul
            if !errorlevel! equ 0 (
                echo MySQL连接成功！
                goto :success
            )
        )
    )
)

echo.
echo ========================================
echo MySQL连接失败！
echo ========================================
echo 可能的原因：
echo 1. MySQL未安装或未添加到PATH
echo 2. MySQL服务未启动
echo 3. 用户名或密码错误
echo 4. 端口3306被占用
echo.
echo 解决方案：
echo 1. 检查MySQL是否已安装
echo 2. 启动MySQL服务：net start MySQL80
echo 3. 重置MySQL root密码
echo 4. 使用MySQL Workbench等工具测试连接
echo.
pause
goto :end

:success
echo.
echo ========================================
echo MySQL连接测试成功！
echo ========================================
echo 现在可以启动Spring Boot应用了
echo.
pause

:end 