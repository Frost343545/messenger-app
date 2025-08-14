# 🚀 Подробный деплой на Railway

## 📋 Предварительные требования

### ✅ Что уже готово:
- GitHub репозиторий: https://github.com/Frost343545/messenger-app
- Все необходимые файлы для Railway
- Python API сервер с MySQL поддержкой

### 🔧 Необходимые файлы для Railway:
- `Procfile` - команда запуска
- `requirements.txt` - Python зависимости
- `mysql_api_server.py` - основной API сервер
- `.gitignore` - исключения Git

## 🚀 Шаг 1: Вход на Railway

1. **Откройте Railway:**
   - Перейдите на https://railway.app
   - Нажмите "Login with GitHub"
   - Авторизуйтесь через ваш аккаунт `Frost343545`

2. **Подтвердите доступ:**
   - Railway запросит доступ к вашим репозиториям
   - Разрешите доступ к `messenger-app`

## 🚀 Шаг 2: Создание проекта

1. **Создайте новый проект:**
   - Нажмите "New Project"
   - Выберите "Deploy from GitHub repo"

2. **Подключите репозиторий:**
   - Найдите `messenger-app` в списке
   - Нажмите "Deploy Now"

3. **Дождитесь деплоя:**
   - Railway автоматически определит Python
   - Установит зависимости из `requirements.txt`
   - Запустит сервер согласно `Procfile`

## 🚀 Шаг 3: Добавление MySQL базы данных

1. **В проекте нажмите "New":**
   - Выберите "Database"
   - Выберите "MySQL"

2. **Настройте базу данных:**
   - Railway автоматически создаст MySQL инстанс
   - Предоставит переменные окружения

3. **Получите данные подключения:**
   - Host, Port, Database, Username, Password
   - Эти данные появятся в Variables

## 🚀 Шаг 4: Настройка переменных окружения

После добавления MySQL, Railway автоматически создаст переменные:

```bash
MYSQL_HOST=your-mysql-host.railway.app
MYSQL_PORT=3306
MYSQL_DATABASE=railway
MYSQL_USERNAME=root
MYSQL_PASSWORD=your-password
```

### 🔧 Дополнительные переменные (если нужны):
```bash
PORT=8080
FLASK_ENV=production
FLASK_DEBUG=false
```

## 🚀 Шаг 5: Проверка деплоя

1. **Дождитесь успешного деплоя:**
   - В логах должно быть: "Application is ready"
   - Статус должен стать "Deployed"

2. **Получите URL:**
   - Railway даст вам URL вида: `https://your-app.railway.app`
   - Скопируйте этот URL

3. **Протестируйте API:**
   - Проверьте: `https://your-app.railway.app/api/health`
   - Должен вернуть: `{"status": "ok", "message": "Messenger API работает!"}`

## 🚀 Шаг 6: Обновление Android приложения

После получения URL от Railway:

1. **Откройте `DatabaseConfig.kt`:**
   ```kotlin
   object DatabaseConfig {
       // Railway Online MySQL Configuration
       const val MYSQL_HOST = "your-app.railway.app"  // Замените на ваш URL
       const val MYSQL_PORT = 3306
       const val MYSQL_DATABASE = "railway"  // Railway использует это имя
       const val MYSQL_USERNAME = "root"      // Railway использует root
       const val MYSQL_PASSWORD = "[Railway password]"

       // API Configuration
       const val API_BASE_URL = "https://your-app.railway.app/api/"
       const val USE_SSL = true
       const val API_TIMEOUT = 30L
   }
   ```

2. **Пересоберите приложение:**
   - В Android Studio: Build → Rebuild Project
   - Установите обновленную версию на устройство

3. **Протестируйте подключение:**
   - Запустите приложение
   - Попробуйте войти/зарегистрироваться
   - Проверьте, что данные сохраняются в онлайн базе

## 🚀 Шаг 7: Мониторинг и обновления

### 📊 Мониторинг:
- **Logs:** Просматривайте логи в Railway Dashboard
- **Metrics:** Следите за использованием ресурсов
- **Health Checks:** API автоматически проверяется

### 🔄 Автоматические обновления:
- При каждом push в `main` ветку GitHub
- Railway автоматически передеплоит приложение
- Без простоя (zero-downtime deployment)

## 🚀 Шаг 8: Устранение неполадок

### ❌ Частые проблемы:

1. **"Build failed":**
   - Проверьте `requirements.txt`
   - Убедитесь, что `Procfile` правильный
   - Проверьте логи сборки

2. **"Database connection failed":**
   - Проверьте переменные окружения
   - Убедитесь, что MySQL запущен
   - Проверьте настройки сети

3. **"Port already in use":**
   - Railway автоматически назначит порт
   - Используйте переменную `PORT` в коде

### 🔧 Полезные команды для отладки:

```bash
# Локальное тестирование
python mysql_api_server.py

# Проверка зависимостей
pip install -r requirements.txt

# Тест подключения к MySQL
python -c "import mysql.connector; print('MySQL OK')"
```

## 🎯 Финальная проверка

После успешного деплоя у вас должно быть:

✅ **Онлайн API:** `https://your-app.railway.app/api/`  
✅ **MySQL база данных:** Автоматически настроена  
✅ **SSL сертификаты:** Автоматически получены  
✅ **Автоматический деплой:** При каждом push в GitHub  
✅ **Мониторинг:** Логи и метрики в реальном времени  

## 🚀 Следующие шаги

1. **Протестируйте все API endpoints**
2. **Обновите Android приложение**
3. **Настройте уведомления о деплоях**
4. **Добавьте мониторинг производительности**

**Поздравляем! Ваш Messenger теперь работает в облаке! 🎉**

---

## 📱 Полезные ссылки

- **Railway Dashboard:** https://railway.app/dashboard
- **GitHub Repository:** https://github.com/Frost343545/messenger-app
- **Railway Documentation:** https://docs.railway.app
- **MySQL Documentation:** https://dev.mysql.com/doc/

## 🆘 Поддержка

Если возникли проблемы:
1. Проверьте логи в Railway Dashboard
2. Убедитесь, что все переменные окружения настроены
3. Проверьте, что MySQL база данных запущена
4. Обратитесь к документации Railway
