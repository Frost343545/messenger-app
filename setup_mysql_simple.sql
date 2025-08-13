-- Простой скрипт настройки MySQL для Messenger приложения
-- Выполните этот скрипт в MySQL Workbench или MySQL Command Line Client

-- 1. Создание базы данных
CREATE DATABASE IF NOT EXISTS messenger_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 2. Использование базы данных
USE messenger_db;

-- 3. Создание пользователя для приложения
CREATE USER IF NOT EXISTS 'messenger_user'@'localhost' IDENTIFIED BY 'messenger_password123';

-- 4. Предоставление прав доступа
GRANT ALL PRIVILEGES ON messenger_db.* TO 'messenger_user'@'localhost';
FLUSH PRIVILEGES;

-- 5. Создание основных таблиц
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    avatar_url TEXT,
    status ENUM('online', 'offline', 'away', 'busy') DEFAULT 'offline',
    is_online BOOLEAN DEFAULT FALSE,
    last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS chats (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    type ENUM('private', 'group', 'channel') DEFAULT 'private',
    avatar_url TEXT,
    created_by VARCHAR(36),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_message_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS messages (
    id VARCHAR(36) PRIMARY KEY,
    chat_id VARCHAR(36) NOT NULL,
    sender_id VARCHAR(36) NOT NULL,
    content TEXT NOT NULL,
    message_type ENUM('text', 'image', 'file', 'voice', 'video') DEFAULT 'text',
    file_url TEXT,
    file_size BIGINT,
    file_name VARCHAR(255),
    reply_to_id VARCHAR(36),
    is_edited BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 6. Добавление тестовых данных
INSERT INTO users (id, name, email, phone, status, is_online) VALUES
('user-001', 'Иван Петров', 'ivan@example.com', '+7-999-123-45-67', 'online', TRUE),
('user-002', 'Мария Сидорова', 'maria@example.com', '+7-999-234-56-78', 'online', TRUE),
('user-003', 'Алексей Козлов', 'alex@example.com', '+7-999-345-67-89', 'away', FALSE);

-- 7. Проверка создания
SELECT 'База данных messenger_db создана успешно!' as status;
SELECT 'Пользователи:' as info;
SELECT id, name, email, status FROM users;
