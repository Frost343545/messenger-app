#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Тест Railway API
"""

import requests
import json

# Railway API URL
RAILWAY_API_URL = "https://web-production-0818c.up.railway.app"

def test_api_health():
    """Тест API здоровья"""
    try:
        print("🔌 Тестирование Railway API...")
        print(f"URL: {RAILWAY_API_URL}")
        print()
        
        # Тест health endpoint
        health_url = f"{RAILWAY_API_URL}/api/health"
        print(f"📡 GET {health_url}")
        
        response = requests.get(health_url, timeout=10)
        
        print(f"📊 Status Code: {response.status_code}")
        print(f"📋 Headers: {dict(response.headers)}")
        
        if response.status_code == 200:
            print("✅ API отвечает!")
            try:
                data = response.json()
                print(f"📄 Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except:
                print(f"📄 Response: {response.text}")
        else:
            print(f"❌ API вернул ошибку: {response.status_code}")
            print(f"📄 Response: {response.text}")
            
    except requests.exceptions.ConnectionError:
        print("❌ Ошибка подключения: приложение не запущено или недоступно")
    except requests.exceptions.Timeout:
        print("❌ Таймаут: приложение не отвечает")
    except Exception as e:
        print(f"❌ Ошибка: {e}")

def test_database_status():
    """Тест статуса базы данных"""
    try:
        print(f"\n🗄️  Тест статуса базы данных...")
        db_url = f"{RAILWAY_API_URL}/api/database/status"
        print(f"📡 GET {db_url}")
        
        response = requests.get(db_url, timeout=10)
        
        print(f"📊 Status Code: {response.status_code}")
        
        if response.status_code == 200:
            print("✅ База данных работает!")
            try:
                data = response.json()
                print(f"📄 Response: {json.dumps(data, indent=2, ensure_ascii=False)}")
            except:
                print(f"📄 Response: {response.text}")
        else:
            print(f"❌ Ошибка базы данных: {response.status_code}")
            print(f"📄 Response: {response.text}")
            
    except Exception as e:
        print(f"❌ Ошибка: {e}")

def test_all_endpoints():
    """Тест всех доступных эндпоинтов"""
    endpoints = [
        "/api/health",
        "/api/database/status",
        "/api/users",
        "/api/chats"
    ]
    
    print(f"\n🔗 Тест всех эндпоинтов...")
    
    for endpoint in endpoints:
        try:
            url = f"{RAILWAY_API_URL}{endpoint}"
            print(f"\n📡 GET {url}")
            
            response = requests.get(url, timeout=10)
            print(f"   Status: {response.status_code}")
            
            if response.status_code == 200:
                print(f"   ✅ {endpoint} работает")
            else:
                print(f"   ❌ {endpoint} ошибка: {response.status_code}")
                
        except Exception as e:
            print(f"   ❌ {endpoint} ошибка: {e}")

if __name__ == "__main__":
    print("🚀 Тест Railway API")
    print("=" * 50)
    
    # Тестируем API
    test_api_health()
    
    # Тестируем базу данных
    test_database_status()
    
    # Тестируем все эндпоинты
    test_all_endpoints()
    
    print("\n🎯 Результаты тестирования:")
    print("✅ Если API отвечает - приложение работает")
    print("❌ Если 'Application not found' - нужно дождаться деплоя")
    print("❌ Если 'Connection error' - приложение не запущено")
    
    print(f"\n🔗 Ваш Railway URL: {RAILWAY_API_URL}")
    print("📱 Откройте в браузере для проверки")
