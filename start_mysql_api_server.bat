@echo off
chcp 65001 >nul
echo 🚀 Запуск Messenger MySQL API Server...
echo.

REM Проверяем, установлен ли Python
python --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Python не установлен! Установите Python 3.7+ с https://python.org
    echo.
    pause
    exit /b 1
)

echo ✅ Python найден!
echo.

REM Устанавливаем зависимости
echo 📦 Установка зависимостей для MySQL...
pip install -r requirements.txt

echo.
echo 🎯 Запуск MySQL API сервера...
echo 📱 API будет доступен по адресу: http://localhost:8080
echo 🗄️  База данных: MySQL (messenger_db)
echo.

REM Запускаем MySQL API сервер
python mysql_api_server.py

pause
