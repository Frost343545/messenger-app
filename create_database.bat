@echo off
chcp 65001 >nul
echo Создание базы данных messenger_db...
echo.

REM Создание базы данных
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -pFrost113ru -e "CREATE DATABASE IF NOT EXISTS messenger_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

echo.
echo База данных создана! Теперь создаем пользователя...
echo.

REM Создание пользователя
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -pFrost113ru -e "CREATE USER IF NOT EXISTS 'messenger_user'@'localhost' IDENTIFIED BY 'messenger_password123';"

echo.
echo Пользователь создан! Предоставляем права...
echo.

REM Предоставление прав
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -pFrost113ru -e "GRANT ALL PRIVILEGES ON messenger_db.* TO 'messenger_user'@'localhost'; FLUSH PRIVILEGES;"

echo.
echo Права предоставлены! Создаем таблицы...
echo.

REM Создание таблиц
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -pFrost113ru messenger_db -e "CREATE TABLE IF NOT EXISTS users (id VARCHAR(36) PRIMARY KEY, name VARCHAR(100) NOT NULL, email VARCHAR(255) UNIQUE NOT NULL, phone VARCHAR(20), status ENUM('online', 'offline', 'away', 'busy') DEFAULT 'offline', is_online BOOLEAN DEFAULT FALSE, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);"

echo.
echo Таблица users создана! Добавляем тестовые данные...
echo.

REM Тестовые данные
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -pFrost113ru messenger_db -e "INSERT INTO users (id, name, email, phone, status, is_online) VALUES ('user-001', 'Иван Петров', 'ivan@example.com', '+7-999-123-45-67', 'online', TRUE), ('user-002', 'Мария Сидорова', 'maria@example.com', '+7-999-234-56-78', 'online', TRUE);"

echo.
echo Тестовые данные добавлены! Проверяем результат...
echo.

REM Проверка
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -pFrost113ru messenger_db -e "SELECT 'База данных messenger_db готова!' as status; SELECT * FROM users;"

echo.
echo Готово! База данных messenger_db создана и настроена!
echo.
pause
