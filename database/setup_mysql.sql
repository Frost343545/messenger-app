-- MySQL Database Setup Script for Messenger App
-- Выполните этот скрипт после подключения к MySQL Server

-- Создание базы данных
CREATE DATABASE IF NOT EXISTS messenger_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Использование базы данных
USE messenger_db;

-- Создание пользователя для приложения
CREATE USER IF NOT EXISTS 'messenger_user'@'localhost' IDENTIFIED BY 'messenger_password123';
CREATE USER IF NOT EXISTS 'messenger_user'@'%' IDENTIFIED BY 'messenger_password123';

-- Предоставление прав доступа
GRANT ALL PRIVILEGES ON messenger_db.* TO 'messenger_user'@'localhost';
GRANT ALL PRIVILEGES ON messenger_db.* TO 'messenger_user'@'%';
FLUSH PRIVILEGES;

-- Создание таблиц
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
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_is_online (is_online)
);

CREATE TABLE IF NOT EXISTS chats (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    type ENUM('private', 'group', 'channel') DEFAULT 'private',
    avatar_url TEXT,
    created_by VARCHAR(36),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_message_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_created_by (created_by),
    INDEX idx_last_message_at (last_message_at),
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS chat_participants (
    chat_id VARCHAR(36),
    user_id VARCHAR(36),
    role ENUM('admin', 'member', 'readonly') DEFAULT 'member',
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (chat_id, user_id),
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_chat_id (chat_id),
    INDEX idx_user_id (user_id)
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
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_chat_id (chat_id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_created_at (created_at),
    INDEX idx_reply_to_id (reply_to_id),
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (reply_to_id) REFERENCES messages(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS message_status (
    message_id VARCHAR(36),
    user_id VARCHAR(36),
    status ENUM('sent', 'delivered', 'read') DEFAULT 'sent',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (message_id, user_id),
    FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_message_id (message_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

CREATE TABLE IF NOT EXISTS auth_tokens (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    token VARCHAR(500) NOT NULL,
    type ENUM('access', 'refresh') DEFAULT 'access',
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_token (token),
    INDEX idx_expires_at (expires_at),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_contacts (
    user_id VARCHAR(36),
    contact_id VARCHAR(36),
    nickname VARCHAR(100),
    is_favorite BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, contact_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_contact_id (contact_id)
);

CREATE TABLE IF NOT EXISTS notifications (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    type ENUM('message', 'friend_request', 'system') DEFAULT 'message',
    is_read BOOLEAN DEFAULT FALSE,
    related_id VARCHAR(36),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_type (type),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Создание триггеров для автоматического обновления времени
DELIMITER //

CREATE TRIGGER update_users_updated_at 
BEFORE UPDATE ON users 
FOR EACH ROW 
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END//

CREATE TRIGGER update_chats_updated_at 
BEFORE UPDATE ON chats 
FOR EACH ROW 
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END//

CREATE TRIGGER update_messages_updated_at 
BEFORE UPDATE ON messages 
FOR EACH ROW 
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END//

CREATE TRIGGER update_chats_last_message 
AFTER INSERT ON messages 
FOR EACH ROW 
BEGIN
    UPDATE chats 
    SET last_message_at = NEW.created_at 
    WHERE id = NEW.chat_id;
END//

DELIMITER ;

-- Вставка тестовых данных
INSERT INTO users (id, name, email, phone, status, is_online) VALUES
('user-001', 'Иван Петров', 'ivan@example.com', '+7-999-123-45-67', 'online', TRUE),
('user-002', 'Мария Сидорова', 'maria@example.com', '+7-999-234-56-78', 'online', TRUE),
('user-003', 'Алексей Козлов', 'alex@example.com', '+7-999-345-67-89', 'away', FALSE),
('user-004', 'Елена Волкова', 'elena@example.com', '+7-999-456-78-90', 'offline', FALSE),
('user-005', 'Дмитрий Соколов', 'dmitry@example.com', '+7-999-567-89-01', 'busy', FALSE);

-- Создание тестовых чатов
INSERT INTO chats (id, name, type, created_by) VALUES
('chat-001', NULL, 'private', 'user-001'),
('chat-002', 'Рабочая группа', 'group', 'user-001'),
('chat-003', NULL, 'private', 'user-002');

-- Добавление участников в чаты
INSERT INTO chat_participants (chat_id, user_id, role) VALUES
('chat-001', 'user-001', 'member'),
('chat-001', 'user-002', 'member'),
('chat-002', 'user-001', 'admin'),
('chat-002', 'user-002', 'member'),
('chat-002', 'user-003', 'member'),
('chat-003', 'user-002', 'member'),
('chat-003', 'user-004', 'member');

-- Добавление тестовых сообщений
INSERT INTO messages (id, chat_id, sender_id, content, message_type) VALUES
('msg-001', 'chat-001', 'user-001', 'Привет! Как дела?', 'text'),
('msg-002', 'chat-001', 'user-002', 'Привет! Все хорошо, спасибо!', 'text'),
('msg-003', 'chat-001', 'user-001', 'Отлично!', 'text'),
('msg-004', 'chat-002', 'user-001', 'Доброе утро, команда!', 'text'),
('msg-005', 'chat-002', 'user-002', 'Доброе утро!', 'text');

-- Добавление статусов сообщений
INSERT INTO message_status (message_id, user_id, status) VALUES
('msg-001', 'user-002', 'read'),
('msg-002', 'user-001', 'read'),
('msg-003', 'user-002', 'delivered'),
('msg-004', 'user-002', 'read'),
('msg-004', 'user-003', 'delivered'),
('msg-005', 'user-001', 'read'),
('msg-005', 'user-003', 'delivered');

-- Добавление контактов
INSERT INTO user_contacts (user_id, contact_id, nickname, is_favorite) VALUES
('user-001', 'user-002', 'Маша', TRUE),
('user-001', 'user-003', 'Алекс', FALSE),
('user-002', 'user-001', 'Ваня', TRUE),
('user-002', 'user-004', 'Лена', FALSE);

-- Проверка созданных данных
SELECT 'Users:' as info;
SELECT id, name, email, status, is_online FROM users;

SELECT 'Chats:' as info;
SELECT c.id, c.name, c.type, u.name as created_by FROM chats c 
LEFT JOIN users u ON c.created_by = u.id;

SELECT 'Messages:' as info;
SELECT m.id, m.content, u.name as sender, c.name as chat_name 
FROM messages m 
JOIN users u ON m.sender_id = u.id 
JOIN chats c ON m.chat_id = c.id;

SELECT 'Database setup completed successfully!' as status;
