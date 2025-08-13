# 🚀 Создание GitHub репозитория для Railway

## 📋 Пошаговая инструкция

### **Шаг 1: Создание GitHub репозитория**

1. **Перейдите на GitHub:**
   - Откройте https://github.com
   - Войдите в свой аккаунт (или создайте новый)

2. **Создайте новый репозиторий:**
   - Нажмите зеленую кнопку **"New"** или **"+"** → **"New repository"**
   - Заполните поля:
     ```
     Repository name: messenger-app
     Description: Android Messenger с MySQL API
     Visibility: Public (для бесплатного Railway)
     ✅ Add a README file
     ✅ Add .gitignore (выберите Python)
     ✅ Choose a license (MIT License)
     ```

3. **Нажмите "Create repository"**

### **Шаг 2: Подключение локального репозитория к GitHub**

После создания репозитория на GitHub, выполните следующие команды в терминале:

```bash
# Добавляем удаленный репозиторий
git remote add origin https://github.com/YOUR_USERNAME/messenger-app.git

# Переименовываем ветку в main (современный стандарт)
git branch -M main

# Отправляем код на GitHub
git push -u origin main
```

**Замените `YOUR_USERNAME` на ваше имя пользователя GitHub!**

### **Шаг 3: Проверка подключения**

```bash
# Проверяем удаленные репозитории
git remote -v

# Должно показать:
# origin  https://github.com/YOUR_USERNAME/messenger-app.git (fetch)
# origin  https://github.com/YOUR_USERNAME/messenger-app.git (push)
```

### **Шаг 4: Деплой на Railway**

После успешной загрузки на GitHub:

1. **Перейдите на Railway:**
   - Откройте https://railway.app
   - Войдите через GitHub

2. **Создайте новый проект:**
   - Нажмите "New Project"
   - Выберите "Deploy from GitHub repo"

3. **Подключите ваш репозиторий:**
   - Выберите `messenger-app` репозиторий
   - Railway автоматически определит Python

4. **Добавьте MySQL базу данных:**
   - В проекте нажмите "New"
   - Выберите "Database" → "MySQL"
   - Railway создаст базу автоматически

5. **Получите URL:**
   - Railway даст вам URL вида: `https://your-app.railway.app`
   - Обновите `DatabaseConfig.kt` в Android приложении

## 🔧 Структура репозитория

```
messenger-app/
├── app/                          # Android приложение
├── database/                     # SQL скрипты
├── mysql_api_server.py          # Python API сервер
├── requirements.txt              # Python зависимости
├── Procfile                     # Для Railway деплоя
├── README.md                    # Описание проекта
├── LICENSE                      # MIT лицензия
├── .gitignore                   # Git исключения
└── .gitattributes              # Git атрибуты
```

## 🚀 Автоматические действия

После подключения к Railway:

- ✅ **Автоматический деплой** при каждом push в main
- ✅ **Встроенная MySQL** база данных
- ✅ **SSL сертификаты** автоматически
- ✅ **Переменные окружения** настроены автоматически

## 📱 Обновление Android приложения

После получения URL от Railway:

1. **Обновите `DatabaseConfig.kt`:**
   ```kotlin
   const val API_BASE_URL = "https://your-app.railway.app/api/"
   ```

2. **Пересоберите приложение** в Android Studio

3. **Протестируйте** подключение к онлайн API

## 🎯 Следующие шаги

1. ✅ **Создайте GitHub репозиторий**
2. ✅ **Подключите локальный код**
3. ✅ **Загрузите на GitHub**
4. 🚀 **Деплойте на Railway**
5. 📱 **Обновите Android приложение**

**Готовы создать онлайн messenger? 🚀**
