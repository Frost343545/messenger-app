#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Простой API сервер для тестирования Messenger приложения
Запуск: python simple_api_server.py
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
import json
import uuid
from datetime import datetime
import sqlite3
import os

app = Flask(__name__)
CORS(app)  # Разрешаем CORS для всех доменов

# База данных SQLite для тестирования
DB_PATH = "messenger_test.db"

def init_database():
    """Инициализация базы данных"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    
    # Создаем таблицы
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS users (
            id TEXT PRIMARY KEY,
            name TEXT NOT NULL,
            email TEXT UNIQUE NOT NULL,
            phone TEXT,
            password_hash TEXT NOT NULL,
            status TEXT DEFAULT 'offline',
            is_online BOOLEAN DEFAULT FALSE,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS chats (
            id TEXT PRIMARY KEY,
            name TEXT,
            type TEXT DEFAULT 'private',
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS messages (
            id TEXT PRIMARY KEY,
            chat_id TEXT NOT NULL,
            sender_id TEXT NOT NULL,
            text TEXT NOT NULL,
            timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (chat_id) REFERENCES chats (id),
            FOREIGN KEY (sender_id) REFERENCES users (id)
        )
    ''')
    
    conn.commit()
    conn.close()

# Инициализируем базу данных при запуске
init_database()

@app.route('/api/health', methods=['GET'])
def health_check():
    """Проверка здоровья сервера"""
    return jsonify({
        "status": "ok",
        "message": "Messenger API Server работает!",
        "timestamp": datetime.now().isoformat()
    })

@app.route('/api/auth/register', methods=['POST'])
def register():
    """Регистрация пользователя"""
    try:
        data = request.get_json()
        
        # Проверяем обязательные поля
        required_fields = ['name', 'email', 'password']
        for field in required_fields:
            if not data.get(field):
                return jsonify({"error": f"Поле {field} обязательно"}), 400
        
        user_id = str(uuid.uuid4())
        
        # Сохраняем пользователя в БД
        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        cursor.execute('''
            INSERT INTO users (id, name, email, phone, password_hash, status)
            VALUES (?, ?, ?, ?, ?, ?)
        ''', (
            user_id,
            data['name'],
            data['email'],
            data.get('phone', ''),
            data['password'],  # В реальном приложении здесь должен быть хеш
            'online'
        ))
        
        conn.commit()
        conn.close()
        
        return jsonify({
            "success": True,
            "message": "Пользователь зарегистрирован",
            "user_id": user_id
        })
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/auth/login', methods=['POST'])
def login():
    """Вход пользователя"""
    try:
        data = request.get_json()
        
        if not data.get('email') or not data.get('password'):
            return jsonify({"error": "Email и пароль обязательны"}), 400
        
        # Проверяем пользователя в БД
        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        cursor.execute('''
            SELECT id, name, email, phone, status FROM users 
            WHERE email = ? AND password_hash = ?
        ''', (data['email'], data['password']))
        
        user = cursor.fetchone()
        conn.close()
        
        if user:
            # Обновляем статус на онлайн
            conn = sqlite3.connect(DB_PATH)
            cursor = conn.cursor()
            cursor.execute('UPDATE users SET status = ?, is_online = TRUE WHERE id = ?', 
                         ('online', user[0]))
            conn.commit()
            conn.close()
            
            return jsonify({
                "success": True,
                "message": "Вход выполнен успешно",
                "user": {
                    "id": user[0],
                    "name": user[1],
                    "email": user[2],
                    "phone": user[3],
                    "status": "online"
                }
            })
        else:
            return jsonify({"error": "Неверный email или пароль"}), 401
            
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/users', methods=['GET'])
def get_users():
    """Получение списка пользователей"""
    try:
        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        cursor.execute('''
            SELECT id, name, email, phone, status, is_online, created_at 
            FROM users ORDER BY name
        ''')
        
        users = []
        for row in cursor.fetchall():
            users.append({
                "id": row[0],
                "name": row[1],
                "email": row[2],
                "phone": row[3],
                "status": row[4],
                "is_online": bool(row[5]),
                "created_at": row[6]
            })
        
        conn.close()
        
        return jsonify({
            "success": True,
            "users": users
        })
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/api/chats', methods=['GET'])
def get_chats():
    """Получение списка чатов"""
    try:
        conn = sqlite3.connect(DB_PATH)
        cursor = conn.cursor()
        
        cursor.execute('''
            SELECT id, name, type, created_at FROM chats ORDER BY created_at DESC
        ''')
        
        chats = []
        for row in cursor.fetchall():
            chats.append({
                "id": row[0],
                "name": row[1],
                "type": row[2],
                "created_at": row[3]
            })
        
        conn.close()
        
        return jsonify({
            "success": True,
            "chats": chats
        })
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    print("🚀 Запуск Messenger API Server...")
    print("📱 API доступен по адресу: http://localhost:8080")
    print("🔗 Проверка здоровья: http://localhost:8080/api/health")
    print("📝 Регистрация: POST http://localhost:8080/api/auth/register")
    print("🔑 Вход: POST http://localhost:8080/api/auth/login")
    print("👥 Пользователи: GET http://localhost:8080/api/users")
    print("💬 Чаты: GET http://localhost:8080/api/chats")
    print("\nНажмите Ctrl+C для остановки сервера")
    
    app.run(host='0.0.0.0', port=8080, debug=True)
