@echo off
chcp 65001 >nul
echo 🚀 Запуск Messenger API Server...
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
echo 📦 Установка зависимостей...
pip install -r requirements.txt

echo.
echo 🎯 Запуск API сервера...
echo 📱 API будет доступен по адресу: http://localhost:8080
echo.

REM Запускаем сервер
python simple_api_server.py

pause
