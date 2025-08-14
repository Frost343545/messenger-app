# 🚀 Railway - Быстрый старт

## ⚡ Экспресс-деплой (5 минут)

### 1️⃣ Вход на Railway
- Откройте: https://railway.app
- Нажмите: "Login with GitHub"
- Авторизуйтесь как `Frost343545`

### 2️⃣ Создание проекта
- Нажмите: "New Project"
- Выберите: "Deploy from GitHub repo"
- Найдите: `messenger-app`
- Нажмите: "Deploy Now"

### 3️⃣ Добавление MySQL
- В проекте нажмите: "New"
- Выберите: "Database" → "MySQL"
- Дождитесь создания

### 4️⃣ Получение URL
- Скопируйте URL вида: `https://your-app.railway.app`
- Протестируйте: `https://your-app.railway.app/api/health`

## 🔧 Обновление Android приложения

### Откройте `DatabaseConfig.kt`:
```kotlin
const val API_BASE_URL = "https://your-app.railway.app/api/"
```

### Пересоберите приложение в Android Studio

## ✅ Проверка работы

1. **API Health:** `GET /api/health`
2. **MySQL Status:** `GET /api/database/status`
3. **Регистрация:** `POST /api/auth/register`
4. **Вход:** `POST /api/auth/login`

## 🆘 Если что-то пошло не так

- Проверьте логи в Railway Dashboard
- Убедитесь, что MySQL запущен
- Проверьте переменные окружения

---

**Подробная инструкция:** `RAILWAY_DEPLOY.md`
**GitHub репозиторий:** https://github.com/Frost343545/messenger-app
