# 📱 Messenger App

Современное Android приложение-мессенджер с облачным API на Render.com и MySQL базой данных на Railway.

## 🚀 **Статус проекта**

✅ **API работает в облаке:** `https://messenger-app-1-4fo0.onrender.com`  
✅ **MySQL база данных:** Railway (shinkansen.proxy.rlwy.net:28429)  
✅ **Автоматический деплой:** При каждом push в GitHub  
✅ **Бесплатный хостинг:** Render.com  

## 🏗️ **Архитектура**

### **Frontend (Android)**
- **Язык:** Kotlin
- **UI Framework:** Jetpack Compose
- **Архитектура:** MVVM
- **База данных:** Room (локальная)
- **Сеть:** Retrofit + OkHttp

### **Backend (API)**
- **Фреймворк:** Flask (Python)
- **База данных:** MySQL
- **Хостинг:** Render.com
- **Автодеплой:** GitHub Actions

## 🔧 **Быстрый старт**

### **1. Клонирование репозитория**
```bash
git clone https://github.com/Frost343545/messenger-app.git
cd messenger-app
```

### **2. Запуск API сервера локально**
```bash
# Установка зависимостей
pip install -r requirements.txt

# Запуск сервера
python mysql_api_server.py
```

### **3. Тестирование API**
```bash
python test_render_api.py
```

### **4. Сборка Android приложения**
Откройте проект в Android Studio и выполните Build → Rebuild Project.

## 🌐 **API Endpoints**

| Метод | Endpoint | Описание |
|-------|----------|----------|
| `GET` | `/api/health` | Проверка здоровья API |
| `GET` | `/api/database/status` | Статус MySQL |
| `GET` | `/api/users` | Список пользователей |
| `GET` | `/api/chats` | Список чатов |
| `POST` | `/api/auth/register` | Регистрация |
| `POST` | `/api/auth/login` | Вход |

## 📱 **Функции приложения**

- 🔐 **Аутентификация:** Регистрация и вход
- 👥 **Пользователи:** Управление профилями
- 💬 **Чаты:** Создание и управление чатами
- 📨 **Сообщения:** Отправка и получение
- 💾 **Синхронизация:** Локальная + облачная база
- 🌍 **Онлайн режим:** Работа через интернет

## 🚀 **Деплой**

### **Render.com (API)**
- Автоматический деплой из GitHub
- Бесплатный хостинг
- SSL сертификаты
- Глобальный CDN

### **Railway (MySQL)**
- Облачная MySQL база данных
- Автоматическое резервное копирование
- SSL соединения

## 📊 **Технологии**

- **Android:** Kotlin, Jetpack Compose, Room, Retrofit
- **Backend:** Python, Flask, MySQL
- **Cloud:** Render.com, Railway
- **CI/CD:** GitHub Actions

## 🔗 **Полезные ссылки**

- **API:** https://messenger-app-1-4fo0.onrender.com
- **Render Dashboard:** https://dashboard.render.com
- **Railway Dashboard:** https://railway.app

## 📄 **Лицензия**

MIT License - см. файл [LICENSE](LICENSE)

---

## 🎯 **Результат**

**Полноценное облачное приложение-мессенджер с:**
- ✅ Современным Android UI
- ✅ Облачным API на Render.com
- ✅ MySQL базой данных на Railway
- ✅ Автоматическим деплоем
- ✅ Бесплатным хостингом

**Готово к использованию! 🚀**
