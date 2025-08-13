-- Тестовый скрипт для проверки подключения к MySQL
-- Выполните этот скрипт для проверки работы базы данных

-- Проверка версии MySQL
SELECT VERSION() as mysql_version;

-- Проверка текущего пользователя
SELECT USER() as current_user;

-- Проверка доступных баз данных
SHOW DATABASES;

-- Проверка прав текущего пользователя
SHOW GRANTS;

-- Проверка статуса сервера
SHOW STATUS LIKE 'Uptime';

-- Проверка переменных сервера
SHOW VARIABLES LIKE 'max_connections';
SHOW VARIABLES LIKE 'wait_timeout';

-- Проверка таблиц в базе messenger_db (если она существует)
USE messenger_db;
SHOW TABLES;

-- Простой тест запроса
SELECT COUNT(*) as total_users FROM users;

-- Проверка подключения завершена успешно!
SELECT 'MySQL connection test completed successfully!' as status;
