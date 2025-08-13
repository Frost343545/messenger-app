# Настройка MySQL для Messenger приложения

## 🗄️ Требования

- MySQL Server 8.0 или новее
- MySQL Workbench (опционально, для управления)
- Доступ к командной строке MySQL

## 📋 Шаги настройки

### 1. Установка MySQL Server

#### Windows:
```bash
# Скачайте MySQL Installer с официального сайта
# https://dev.mysql.com/downloads/installer/
# Запустите установщик и следуйте инструкциям
```

#### macOS:
```bash
# Используя Homebrew
brew install mysql

# Запуск сервиса
brew services start mysql
```

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mysql-server

# Запуск сервиса
sudo systemctl start mysql
sudo systemctl enable mysql
```

### 2. Настройка безопасности

```bash
# Запуск скрипта безопасности
sudo mysql_secure_installation

# Войдите в MySQL
sudo mysql -u root -p
```

### 3. Создание базы данных и пользователя

```sql
-- Создание базы данных
CREATE DATABASE messenger_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Создание пользователя для приложения
CREATE USER 'messenger_user'@'%' IDENTIFIED BY 'your_secure_password';

-- Предоставление прав доступа
GRANT ALL PRIVILEGES ON messenger_db.* TO 'messenger_user'@'%';
FLUSH PRIVILEGES;

-- Проверка пользователей
SELECT User, Host FROM mysql.user;
```

### 4. Импорт схемы базы данных

```bash
# Импорт SQL файла
mysql -u messenger_user -p messenger_db < database/schema.sql

# Или выполните команды вручную в MySQL Workbench
```

### 5. Настройка конфигурации приложения

Откройте файл `app/src/main/java/com/messenger/app/data/remote/config/DatabaseConfig.kt` и измените следующие параметры:

```kotlin
object DatabaseConfig {
    // MySQL Server Configuration
    const val MYSQL_HOST = "your-actual-mysql-server.com" // или "localhost"
    const val MYSQL_PORT = 3306
    const val MYSQL_DATABASE = "messenger_db"
    const val MYSQL_USERNAME = "messenger_user"
    const val MYSQL_PASSWORD = "your_secure_password"
    
    // API Configuration (если используете REST API)
    const val API_BASE_URL = "http://your-api-server.com/api/"
}
```

### 6. Настройка сетевого доступа

#### Настройка bind-address (если нужно удаленное подключение):

```bash
# Откройте файл конфигурации MySQL
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf

# Найдите строку bind-address и измените на:
bind-address = 0.0.0.0

# Перезапустите MySQL
sudo systemctl restart mysql
```

#### Настройка файрвола:

```bash
# Ubuntu/Debian
sudo ufw allow 3306

# CentOS/RHEL
sudo firewall-cmd --permanent --add-port=3306/tcp
sudo firewall-cmd --reload
```

### 7. Проверка подключения

```bash
# Тест подключения
mysql -h your-mysql-server.com -u messenger_user -p messenger_db

# Проверка таблиц
USE messenger_db;
SHOW TABLES;
```

## 🔧 Дополнительные настройки

### Оптимизация производительности

Добавьте в `my.cnf`:

```ini
[mysqld]
# InnoDB настройки
innodb_buffer_pool_size = 1G
innodb_log_file_size = 256M
innodb_flush_log_at_trx_commit = 2

# Настройки подключений
max_connections = 200
max_connect_errors = 1000000

# Настройки кэша
query_cache_size = 64M
query_cache_type = 1
```

### Резервное копирование

```bash
# Создание резервной копии
mysqldump -u messenger_user -p messenger_db > backup_$(date +%Y%m%d_%H%M%S).sql

# Восстановление из резервной копии
mysql -u messenger_user -p messenger_db < backup_file.sql
```

## 🚨 Безопасность

### 1. Пароли
- Используйте сложные пароли
- Регулярно меняйте пароли
- Не используйте пароли по умолчанию

### 2. Доступ
- Ограничьте доступ только необходимыми IP адресами
- Используйте SSL для подключений
- Регулярно проверяйте права доступа

### 3. Мониторинг
```sql
-- Проверка активных подключений
SHOW PROCESSLIST;

-- Проверка прав пользователей
SHOW GRANTS FOR 'messenger_user'@'%';
```

## 📱 Тестирование в приложении

1. Запустите приложение
2. Перейдите на экран входа
3. Попробуйте зарегистрировать нового пользователя
4. Проверьте, что данные сохраняются в MySQL

## 🐛 Устранение неполадок

### Ошибка подключения:
```
Error: Can't connect to MySQL server
```
**Решение:** Проверьте:
- Запущен ли MySQL сервис
- Правильность IP адреса и порта
- Настройки файрвола
- Права доступа пользователя

### Ошибка аутентификации:
```
Error: Access denied for user
```
**Решение:** Проверьте:
- Правильность имени пользователя и пароля
- Права доступа к базе данных
- Хост, с которого подключаетесь

### Ошибка SSL:
```
Error: SSL connection error
```
**Решение:** Проверьте:
- Настройки SSL в MySQL
- Сертификаты
- Или отключите SSL для тестирования

## 📞 Поддержка

Если у вас возникли проблемы:
1. Проверьте логи MySQL: `sudo tail -f /var/log/mysql/error.log`
2. Убедитесь, что все сервисы запущены
3. Проверьте настройки сети и файрвола
4. Обратитесь к документации MySQL

---

**Успешной настройки! 🎉**
