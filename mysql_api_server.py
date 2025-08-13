#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
MySQL API сервер для Messenger приложения
Запуск: python mysql_api_server.py
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
import json
import uuid
from datetime import datetime
import mysql.connector
from mysql.connector import Error
import os

app = Flask(__name__)
CORS(app)  # Разрешаем CORS для всех доменов

# MySQL Configuration
MYSQL_CONFIG = {
    'host': 'localhost',
    'port': 3306,
    'database': 'messenger_db',
    'user': 'messenger_user',
    'password': 'messenger_password123',
    'charset': 'utf8mb4',
    'autocommit': True
}

def get_db_connection():
    """Создание соединения с MySQL"""
    try:
        connection = mysql.connector.connect(**MYSQL_CONFIG)
        return connection
    except Error as e:
        print(f"Ошибка подключения к MySQL: {e}")
        return None

def init_database():
    """Инициализация базы данных MySQL"""
    try:
        connection = get_db_connection()
        if connection is None:
            return False
            
        cursor = connection.cursor()
        
        # Создаем таблицы, если их нет
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
        
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS chats (
                id VARCHAR(36) PRIMARY KEY,
                name VARCHAR(100),
                type ENUM('private', 'group') DEFAULT 'private',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
        ''')
        
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
        
        print("✅ База данных MySQL инициализирована успешно!")
        return True
        
    except Error as e:
        print(f"❌ Ошибка инициализации MySQL: {e}")
        return False

@app.route('/api/health', methods=['GET'])
def health_check():
    """Проверка здоровья сервера и подключения к MySQL"""
    try:
        connection = get_db_connection()
        if connection is None:
            return jsonify({
                "status": "error",
                "message": "Не удается подключиться к MySQL",
                "timestamp": datetime.now().isoformat()
            }), 500
            
        cursor = connection.cursor()
        cursor.execute("SELECT 1")
        cursor.fetchone()
        cursor.close()
        connection.close()
        
        return jsonify({
            "status": "ok",
            "message": "Messenger MySQL API Server работает!",
            "database": "MySQL подключен",
            "timestamp": datetime.now().isoformat()
        })
        
    except Error as e:
        return jsonify({
            "status": "error",
            "message": f"Ошибка MySQL: {str(e)}",
            "timestamp": datetime.now().isoformat()
        }), 500

@app.route('/api/auth/register', methods=['POST'])
def register():
    """Регистрация пользователя в MySQL"""
    try:
        data = request.get_json()
        
        # Проверяем обязательные поля
        required_fields = ['name', 'email', 'password']
        for field in required_fields:
            if not data.get(field):
                return jsonify({"error": f"Поле {field} обязательно"}), 400
        
        user_id = str(uuid.uuid4())
        
        # Сохраняем пользователя в MySQL
        connection = get_db_connection()
        if connection is None:
            return jsonify({"error": "Ошибка подключения к базе данных"}), 500
            
        cursor = connection.cursor()
        
        cursor.execute('''
            INSERT INTO users (id, name, email, phone, password_hash, status)
            VALUES (%s, %s, %s, %s, %s, %s)
        ''', (
            user_id,
            data['name'],
            data['email'],
            data.get('phone', ''),
            data['password'],  # В реальном приложении здесь должен быть хеш
            'online'
        ))
        
        connection.commit()
        cursor.close()
        connection.close()
        
        return jsonify({
            "success": True,
            "message": "Пользователь зарегистрирован в MySQL",
            "user_id": user_id
        })
        
    except Error as e:
        if e.errno == 1062:  # Duplicate entry
            return jsonify({"error": "Пользователь с таким email уже существует"}), 409
        return jsonify({"error": f"Ошибка MySQL: {str(e)}"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/auth/login', methods=['POST'])
def login():
    """Вход пользователя из MySQL"""
    try:
        data = request.get_json()
        
        if not data.get('email') or not data.get('password'):
            return jsonify({"error": "Email и пароль обязательны"}), 400
        
        # Проверяем пользователя в MySQL
        connection = get_db_connection()
        if connection is None:
            return jsonify({"error": "Ошибка подключения к базе данных"}), 500
            
        cursor = connection.cursor(dictionary=True)
        
        cursor.execute('''
            SELECT id, name, email, phone, status FROM users 
            WHERE email = %s AND password_hash = %s
        ''', (data['email'], data['password']))
        
        user = cursor.fetchone()
        
        if user:
            # Обновляем статус на онлайн
            cursor.execute('''
                UPDATE users SET status = %s, is_online = TRUE WHERE id = %s
            ''', ('online', user['id']))
            
            connection.commit()
            cursor.close()
            connection.close()
            
            return jsonify({
                "success": True,
                "message": "Вход выполнен успешно из MySQL",
                "user": {
                    "id": user['id'],
                    "name": user['name'],
                    "email": user['email'],
                    "phone": user['phone'],
                    "status": "online"
                }
            })
        else:
            cursor.close()
            connection.close()
            return jsonify({"error": "Неверный email или пароль"}), 401
            
    except Error as e:
        return jsonify({"error": f"Ошибка MySQL: {str(e)}"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/users', methods=['GET'])
def get_users():
    """Получение списка пользователей из MySQL"""
    try:
        connection = get_db_connection()
        if connection is None:
            return jsonify({"error": "Ошибка подключения к базе данных"}), 500
            
        cursor = connection.cursor(dictionary=True)
        
        cursor.execute('''
            SELECT id, name, email, phone, status, is_online, created_at 
            FROM users ORDER BY name
        ''')
        
        users = cursor.fetchall()
        
        cursor.close()
        connection.close()
        
        return jsonify({
            "success": True,
            "users": users,
            "count": len(users)
        })
        
    except Error as e:
        return jsonify({"error": f"Ошибка MySQL: {str(e)}"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/chats', methods=['GET'])
def get_chats():
    """Получение списка чатов из MySQL"""
    try:
        connection = get_db_connection()
        if connection is None:
            return jsonify({"error": "Ошибка подключения к базе данных"}), 500
            
        cursor = connection.cursor(dictionary=True)
        
        cursor.execute('''
            SELECT id, name, type, created_at FROM chats ORDER BY created_at DESC
        ''')
        
        chats = cursor.fetchall()
        
        cursor.close()
        connection.close()
        
        return jsonify({
            "success": True,
            "chats": chats,
            "count": len(chats)
        })
        
    except Error as e:
        return jsonify({"error": f"Ошибка MySQL: {str(e)}"}), 500
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/database/status', methods=['GET'])
def database_status():
    """Статус подключения к MySQL"""
    try:
        connection = get_db_connection()
        if connection is None:
            return jsonify({
                "status": "disconnected",
                "message": "Нет подключения к MySQL"
            })
            
        cursor = connection.cursor()
        cursor.execute("SELECT VERSION()")
        version = cursor.fetchone()[0]
        
        cursor.execute("SELECT COUNT(*) FROM users")
        user_count = cursor.fetchone()[0]
        
        cursor.execute("SELECT COUNT(*) FROM chats")
        chat_count = cursor.fetchone()[0]
        
        cursor.close()
        connection.close()
        
        return jsonify({
            "status": "connected",
            "message": "MySQL подключен успешно",
            "version": version,
            "database": "messenger_db",
            "users_count": user_count,
            "chats_count": chat_count,
            "timestamp": datetime.now().isoformat()
        })
        
    except Error as e:
        return jsonify({
            "status": "error",
            "message": f"Ошибка MySQL: {str(e)}"
        })

if __name__ == '__main__':
    print("🚀 Запуск Messenger MySQL API Server...")
    print("📱 API будет доступен по адресу: http://localhost:8080")
    print("🗄️  База данных: MySQL (messenger_db)")
    print("👤 Пользователь: messenger_user")
    print()
    
    # Инициализируем базу данных
    if init_database():
        print("✅ База данных готова!")
    else:
        print("❌ Ошибка инициализации базы данных!")
        exit(1)
    
    print()
    print("🔗 Доступные эндпоинты:")
    print("   GET  /api/health - Проверка здоровья")
    print("   GET  /api/database/status - Статус MySQL")
    print("   POST /api/auth/register - Регистрация")
    print("   POST /api/auth/login - Вход")
    print("   GET  /api/users - Список пользователей")
    print("   GET  /api/chats - Список чатов")
    print()
    print("Нажмите Ctrl+C для остановки сервера")
    
    app.run(host='0.0.0.0', port=8080, debug=True)
