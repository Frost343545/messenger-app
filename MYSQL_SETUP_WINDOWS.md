# 🗄️ Настройка MySQL на Windows для Messenger приложения

## 📋 Предварительные требования
- Windows 10/11
- MySQL Server 8.0+ (уже установлен)
- MySQL Workbench (опционально, для удобства управления)

## 🚀 Пошаговая настройка

### 1. Запуск MySQL Server

#### Вариант A: Через службы Windows
1. Нажмите `Win + R`, введите `services.msc`
2. Найдите службу `MySQL80` или `MySQL`
3. Правый клик → `Запустить`
4. Установите тип запуска `Автоматически`

#### Вариант B: Через командную строку
```cmd
# Запуск службы MySQL
net start MySQL80

# Проверка статуса
sc query MySQL80
```

### 2. Подключение к MySQL

#### Вариант A: MySQL Command Line Client
1. Пуск → MySQL → MySQL Command Line Client
2. Введите пароль root пользователя
3. Выполните команды из `database/setup_mysql.sql`

#### Вариант B: MySQL Workbench
1. Запустите MySQL Workbench
2. Создайте новое подключение:
   - Hostname: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: ваш пароль root

### 3. Создание базы данных

Откройте файл `database/setup_mysql.sql` и выполните его содержимое:

```sql
-- Создание базы данных
CREATE DATABASE IF NOT EXISTS messenger_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Создание пользователя
CREATE USER IF NOT EXISTS 'messenger_user'@'localhost' 
IDENTIFIED BY 'messenger_password123';

-- Предоставление прав
GRANT ALL PRIVILEGES ON messenger_db.* TO 'messenger_user'@'localhost';
FLUSH PRIVILEGES;
```

### 4. Проверка подключения

Выполните тестовый скрипт `database/test_connection.sql`:

```sql
-- Проверка версии
SELECT VERSION() as mysql_version;

-- Проверка баз данных
SHOW DATABASES;

-- Проверка таблиц
USE messenger_db;
SHOW TABLES;
```

## 🔧 Настройка безопасности

### 1. Изменение пароля root
```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'новый_сложный_пароль';
FLUSH PRIVILEGES;
```

### 2. Создание пользователя только для чтения (опционально)
```sql
CREATE USER 'messenger_readonly'@'localhost' IDENTIFIED BY 'readonly_password';
GRANT SELECT ON messenger_db.* TO 'messenger_readonly'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Ограничение доступа по IP (для продакшена)
```sql
-- Разрешить доступ только с локального хоста
REVOKE ALL PRIVILEGES ON messenger_db.* FROM 'messenger_user'@'%';
DROP USER 'messenger_user'@'%';
FLUSH PRIVILEGES;
```

## 🌐 Настройка сетевого доступа

### 1. Проверка привязки к адресам
```sql
SHOW VARIABLES LIKE 'bind_address';
```

### 2. Изменение привязки (если нужно)
```sql
-- В my.ini файле добавьте:
-- bind-address = 0.0.0.0
```

### 3. Настройка Windows Firewall
1. Откройте Windows Defender Firewall
2. Дополнительные параметры → Правила для входящих подключений
3. Создать правило → Для порта
4. TCP, порт 3306
5. Разрешить подключение
6. Применить ко всем профилям
7. Имя: "MySQL Server"

## 📊 Оптимизация производительности

### 1. Основные настройки в my.ini
```ini
[mysqld]
# Размер буфера для InnoDB
innodb_buffer_pool_size = 256M

# Максимальное количество подключений
max_connections = 200

# Таймаут неактивных подключений
wait_timeout = 600

# Размер кэша запросов
query_cache_size = 64M
query_cache_type = 1
```

### 2. Проверка текущих настроек
```sql
SHOW VARIABLES LIKE 'innodb_buffer_pool_size';
SHOW VARIABLES LIKE 'max_connections';
SHOW VARIABLES LIKE 'query_cache_size';
```

## 🔍 Устранение неполадок

### 1. MySQL не запускается
```cmd
# Проверка логов
type "C:\ProgramData\MySQL\MySQL Server 8.0\Data\COMPUTERNAME.err"

# Перезапуск службы
net stop MySQL80
net start MySQL80
```

### 2. Ошибка подключения
```cmd
# Проверка статуса службы
sc query MySQL80

# Проверка порта
netstat -an | findstr 3306
```

### 3. Ошибка аутентификации
```sql
-- Сброс пароля root (только в крайнем случае!)
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'новый_пароль';
FLUSH PRIVILEGES;
```

## 📱 Тестирование с Android приложением

### 1. Проверка конфигурации
Убедитесь, что в `DatabaseConfig.kt` указаны правильные параметры:
```kotlin
const val MYSQL_HOST = "localhost"  // или IP вашего компьютера
const val MYSQL_PORT = 3306
const val MYSQL_DATABASE = "messenger_db"
const val MYSQL_USERNAME = "messenger_user"
const val MYSQL_PASSWORD = "messenger_password123"
```

### 2. Тест подключения
Запустите приложение и попробуйте войти в систему. Проверьте логи на наличие ошибок подключения.

## 🚨 Важные замечания

1. **Безопасность**: Не используйте простые пароли в продакшене
2. **Резервное копирование**: Регулярно создавайте бэкапы базы данных
3. **Мониторинг**: Следите за использованием ресурсов MySQL
4. **Обновления**: Регулярно обновляйте MySQL Server

## 📞 Поддержка

Если возникли проблемы:
1. Проверьте логи MySQL
2. Убедитесь, что служба запущена
3. Проверьте настройки брандмауэра
4. Обратитесь к документации MySQL

## ✅ Чек-лист завершения

- [ ] MySQL Server установлен и запущен
- [ ] Служба MySQL настроена на автозапуск
- [ ] База данных `messenger_db` создана
- [ ] Пользователь `messenger_user` создан
- [ ] Права доступа настроены
- [ ] Таблицы созданы
- [ ] Тестовые данные добавлены
- [ ] Подключение протестировано
- [ ] Брандмауэр настроен
- [ ] Приложение подключается к базе данных

**База данных готова к использованию! 🎉**
