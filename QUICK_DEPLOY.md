# ⚡ БЫСТРЫЙ ДЕПЛОЙ НА RAILWAY (5 минут)

## 🚨 ПРОБЛЕМА:
URL `https://web-production-0818c.up.railway.app` возвращает "Application not found"

## 🚀 РЕШЕНИЕ:

### 1️⃣ **Откройте:** https://railway.app
### 2️⃣ **Войдите через GitHub** (Frost343545)
### 3️⃣ **Нажмите:** "New Project"
### 4️⃣ **Выберите:** "Deploy from GitHub repo"
### 5️⃣ **Найдите:** `messenger-app`
### 6️⃣ **Нажмите:** "Deploy Now"
### 7️⃣ **Дождитесь:** Статус "Deployed"

## 🔧 **После деплоя добавьте переменные:**

```bash
MYSQL_HOST=shinkansen.proxy.rlwy.net
MYSQL_PORT=28429
MYSQL_DATABASE=railway
MYSQL_USERNAME=root
MYSQL_PASSWORD=QhRDpvMOnPOfPgxooBtgouZighZmZORL
PORT=8080
FLASK_ENV=production
FLASK_DEBUG=false
```

## ✅ **Проверьте:**
- Статус: "Deployed"
- Логи: "Сервер запущен и готов к работе!"
- Новый URL от Railway

## 🔄 **Обновите Android приложение:**
В `DatabaseConfig.kt` замените URL на новый от Railway

---

**🚀 ДЕПЛОЙТЕ СЕЙЧАС - ВАШ MESSENGER БУДЕТ РАБОТАТЬ!**
