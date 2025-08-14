# ⚡ БЫСТРЫЙ СТАРТ НА RENDER.COM (5 минут)

## 🚀 **Прямо сейчас:**

### 1️⃣ **Откройте:** https://render.com
### 2️⃣ **Войдите через GitHub** (Frost343545)
### 3️⃣ **Нажмите:** "New +"
### 4️⃣ **Выберите:** "Web Service"
### 5️⃣ **Подключите репозиторий:** `messenger-app`
### 6️⃣ **Настройте:**
   - **Name:** `messenger-api`
   - **Environment:** `Python 3`
   - **Build Command:** `pip install -r requirements.txt`
   - **Start Command:** `gunicorn mysql_api_server:app`
### 7️⃣ **Добавьте переменные:**
```bash
MYSQL_HOST=shinkansen.proxy.rlwy.net
MYSQL_PORT=28429
MYSQL_DATABASE=railway
MYSQL_USERNAME=root
MYSQL_PASSWORD=QhRDpvMOnPOfPgxooBtgouZighZmZORL
FLASK_ENV=production
FLASK_DEBUG=false
```
### 8️⃣ **Нажмите:** "Create Web Service"
### 9️⃣ **Дождитесь:** Статус "Live"

## ✅ **После деплоя:**
- **URL:** `https://your-app-name.onrender.com`
- **API:** `https://your-app-name.onrender.com/api/health`
- **Обновите Android приложение** с новым URL

---

**🚀 ДЕПЛОЙТЕ СЕЙЧАС - БЕСПЛАТНО!**
