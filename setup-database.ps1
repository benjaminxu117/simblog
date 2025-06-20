# 设置控制台编码为UTF-8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

Write-Host "========================================" -ForegroundColor Green
Write-Host "SimBlog 数据库设置脚本" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Write-Host ""
Write-Host "1. 检查MySQL服务状态..." -ForegroundColor Yellow
$mysqlService = Get-Service -Name "*MySQL*" -ErrorAction SilentlyContinue
if ($mysqlService) {
    Write-Host "MySQL服务状态: $($mysqlService.Status)" -ForegroundColor Green
} else {
    Write-Host "未找到MySQL服务" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "2. 尝试连接MySQL..." -ForegroundColor Yellow

# 尝试常见的MySQL安装路径
$mysqlPaths = @(
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe",
    "C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe",
    "C:\xampp\mysql\bin\mysql.exe",
    "C:\wamp\bin\mysql\mysql5.7.36\bin\mysql.exe"
)

$mysqlExe = $null
foreach ($path in $mysqlPaths) {
    if (Test-Path $path) {
        $mysqlExe = $path
        Write-Host "找到MySQL: $path" -ForegroundColor Green
        break
    }
}

if (-not $mysqlExe) {
    Write-Host "未找到MySQL可执行文件，请确保MySQL已安装" -ForegroundColor Red
    exit 1
}

# 测试连接
Write-Host ""
Write-Host "3. 测试MySQL连接..." -ForegroundColor Yellow
try {
    $result = & $mysqlExe -u root -ppassword -e "SELECT VERSION();" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "MySQL连接成功！" -ForegroundColor Green
        Write-Host "MySQL版本: $result" -ForegroundColor Green
    } else {
        Write-Host "MySQL连接失败，请检查密码" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "MySQL连接失败: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "4. 创建数据库和表..." -ForegroundColor Yellow

# 读取SQL脚本
$sqlScript = Get-Content "create-database.sql" -Raw -Encoding UTF8

try {
    # 执行SQL脚本
    $result = & $mysqlExe -u root -ppassword -e $sqlScript 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "数据库和表创建成功！" -ForegroundColor Green
    } else {
        Write-Host "数据库创建失败" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "数据库创建失败: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "5. 验证数据..." -ForegroundColor Yellow
try {
    $result = & $mysqlExe -u root -ppassword -e "USE simblog; SHOW TABLES; SELECT COUNT(*) as user_count FROM user; SELECT COUNT(*) as article_count FROM article;" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "数据验证成功！" -ForegroundColor Green
        Write-Host $result -ForegroundColor Cyan
    }
} catch {
    Write-Host "数据验证失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "数据库设置完成！" -ForegroundColor Green
Write-Host "现在可以启动Spring Boot应用了" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Write-Host ""
Write-Host "按任意键继续..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") 