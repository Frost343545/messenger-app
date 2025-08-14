#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Тест подключения к Railway MySQL
"""

import mysql.connector
from mysql.connector import Error
import os

# Railway MySQL Configuration
MYSQL_CONFIG = {
    'host': 'shinkansen.proxy.rlwy.net',
    'port': 28429,
    'database': 'railway',
    'user': 'root',
    'password': 'QhRDpvMOnPOfPgxooBtgouZighZmZORL',
    'charset': 'utf8mb4',
    'autocommit': True
}

def test_connection():
    """Тест подключения к Railway MySQL"""
    try:
        print("🔌 Тестирование подключения к Railway MySQL...")
        print(f"Host: {MYSQL_CONFIG['host']}")
        print(f"Port: {MYSQL_CONFIG['port']}")
        print(f"Database: {MYSQL_CONFIG['database']}")
        print(f"User: {MYSQL_CONFIG['user']}")
        print()
        
        # Подключаемся к MySQL
        connection = mysql.connector.connect(**MYSQL_CONFIG)
        
        if connection.is_connected():
            print("✅ Подключение к MySQL успешно!")
            
            # Получаем информацию о сервере
            cursor = connection.cursor()
            cursor.execute("SELECT VERSION()")
            version = cursor.fetchone()[0]
            print(f"📊 MySQL Version: {version}")
            
            # Проверяем текущую базу данных
            cursor.execute("SELECT DATABASE()")
            current_db = cursor.fetchone()[0]
            print(f"🗄️  Current Database: {current_db}")
            
            # Показываем таблицы
            cursor.execute("SHOW TABLES")
            tables = cursor.fetchall()
            print(f"📋 Tables: {len(tables)}")
            for table in tables:
                print(f"   - {table[0]}")
            
            cursor.close()
            connection.close()
            print("\n🎉 Тест подключения завершен успешно!")
            return True
            
    except Error as e:
        print(f"❌ Ошибка подключения к MySQL: {e}")
        return False
    except Exception as e:
        print(f"❌ Неожиданная ошибка: {e}")
        return False

def create_test_tables():
    """Создание тестовых таблиц"""
    try:
        print("\n🔧 Создание тестовых таблиц...")
        
        connection = mysql.connector.connect(**MYSQL_CONFIG)
        cursor = connection.cursor()
        
        # Создаем таблицу users если её нет
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS users (
                id VARCHAR(36) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                phone VARCHAR(20),
                password_hash VARCHAR(255) NOT NULL,
                status ENUM('online', 'offline', 'away', 'busy') DEFAULT 'offline',
                is_online BOOLEAN DEFAULT FALSE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        ''')
        
        # Создаем таблицу chats если её нет
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS chats (
                id VARCHAR(36) PRIMARY KEY,
                name VARCHAR(100),
                type ENUM('private', 'group') DEFAULT 'private',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        ''')
        
        # Создаем таблицу messages если её нет
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS messages (
                id VARCHAR(36) PRIMARY KEY,
                chat_id VARCHAR(36) NOT NULL,
                sender_id VARCHAR(36) NOT NULL,
                text TEXT NOT NULL,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
                FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        ''')
        
        connection.commit()
        cursor.close()
        connection.close()
        
        print("✅ Тестовые таблицы созданы успешно!")
        return True
        
    except Error as e:
        print(f"❌ Ошибка создания таблиц: {e}")
        return False

if __name__ == "__main__":
    print("🚀 Тест подключения к Railway MySQL")
    print("=" * 50)
    
    # Тестируем подключение
    if test_connection():
        # Создаем тестовые таблицы
        create_test_tables()
        
        print("\n🎯 Следующие шаги:")
        print("1. Добавьте переменные окружения в Railway Dashboard")
        print("2. Деплойте API сервер на Railway")
        print("3. Обновите Android приложение")
        print("4. Протестируйте подключение")
    else:
        print("\n❌ Не удалось подключиться к MySQL")
        print("Проверьте:")
        print("- Правильность данных подключения")
        print("- Доступность MySQL сервера")
        print("- Настройки сети")
