@echo off
chcp 65001 >nul
echo 🚀 Подготовка репозитория для Railway...
echo.

REM Проверяем, установлен ли Git
git --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Git не установлен! Скачайте с https://git-scm.com
    echo.
    pause
    exit /b 1
)

echo ✅ Git найден!
echo.

REM Проверяем, есть ли уже Git репозиторий
if exist ".git" (
    echo ⚠️  Git репозиторий уже существует!
    echo.
    choice /C YN /M "Хотите пересоздать репозиторий? (Y/N)"
    if errorlevel 2 (
        echo Отмена операции.
        pause
        exit /b 0
    )
    echo Удаляем старый репозиторий...
    rmdir /s /q ".git"
)

echo.
echo 📁 Создаем .gitignore для Python...
echo # Python
echo __pycache__/
echo *.py[cod]
echo *$py.class
echo *.so
echo .Python
echo build/
echo develop-eggs/
echo dist/
echo downloads/
echo eggs/
echo .eggs/
echo lib/
echo lib64/
echo parts/
echo sdist/
echo var/
echo wheels/
echo *.egg-info/
echo .installed.cfg
echo *.egg
echo MANIFEST
echo.
echo # Virtual Environment
echo .venv/
echo venv/
echo ENV/
echo env/
echo.
echo # IDE
echo .idea/
echo .vscode/
echo *.swp
echo *.swo
echo.
echo # OS
echo .DS_Store
echo Thumbs.db
echo.
echo # Android
echo app/build/
echo .gradle/
echo local.properties
echo.
echo # Logs
echo *.log
echo logs/
echo.
echo # Database
echo *.db
echo *.sqlite
echo *.sqlite3
) > .gitignore

echo.
echo 📝 Создаем README.md...
echo # 📱 Messenger App - Android + MySQL API
echo.
echo ## 🚀 Онлайн мессенджер с MySQL базой данных
echo.
echo ### ✨ Возможности:
echo - 🔐 Аутентификация пользователей
echo - 💬 Чаты и сообщения
echo - 👥 Управление контактами
echo - 📱 Современный Material Design 3 UI
echo - 🗄️ MySQL база данных
echo - 🌐 REST API сервер
echo.
echo ### 🛠️ Технологии:
echo - **Android:** Kotlin, Jetpack Compose, MVVM
echo - **Backend:** Python Flask, MySQL
echo - **Архитектура:** Room + Retrofit + Hilt
echo.
echo ### 🚀 Быстрый старт:
echo.
echo #### 1. Клонируйте репозиторий:
echo ```bash
echo git clone https://github.com/YOUR_USERNAME/messenger-app.git
echo cd messenger-app
echo ```
echo.
echo #### 2. Запустите API сервер:
echo ```bash
echo pip install -r requirements.txt
echo python mysql_api_server.py
echo ```
echo.
echo #### 3. Откройте Android Studio и запустите приложение
echo.
echo ### 🌐 Онлайн версия:
echo - **API:** https://your-app.railway.app
echo - **База данных:** MySQL на Railway
echo.
echo ### 📱 Скриншоты:
echo [Добавьте скриншоты вашего приложения]
echo.
echo ### 🤝 Вклад в проект:
echo 1. Fork репозитория
echo 2. Создайте feature branch
echo 3. Commit изменения
echo 4. Push в branch
echo 5. Создайте Pull Request
echo.
echo ### 📄 Лицензия:
echo MIT License - см. файл LICENSE
echo.
echo ---
echo Создано с ❤️ для изучения Android разработки
) > README.md

echo.
echo 🔧 Создаем .gitattributes...
echo # Auto detect text files and perform LF normalization
echo * text=auto
echo.
echo # Python files
echo *.py text diff=python
echo.
echo # Android files
echo *.kt text
echo *.xml text
echo *.gradle text
echo.
echo # Documentation
echo *.md text diff=markdown
echo *.txt text
) > .gitattributes

echo.
echo 📋 Создаем CHANGELOG.md...
echo # 📝 Журнал изменений
echo.
echo ## [Unreleased]
echo ### Добавлено
echo - 🚀 Базовая структура Android приложения
echo - 🔐 Система аутентификации
echo - 💬 Интерфейс чатов
echo - 👥 Управление контактами
echo - 🗄️ MySQL API сервер
echo - 🌐 Подготовка для Railway деплоя
echo.
echo ### Изменено
echo - Обновлены зависимости до последних версий
echo - Улучшен UI/UX с Material Design 3
echo.
echo ### Исправлено
echo - Решены проблемы с Kapt и Room
echo - Исправлены ошибки компиляции
echo - Настроена сетевая безопасность
echo.
echo ---
echo ## [1.0.0] - 2024-08-14
echo ### Добавлено
echo - 🎉 Первый релиз Messenger приложения
echo - 📱 Основной функционал мессенджера
echo - 🗄️ MySQL база данных
echo - 🌐 REST API
) > CHANGELOG.md

echo.
echo 🎯 Инициализируем Git репозиторий...
git init
git add .
git commit -m "🚀 Инициальный коммит: Messenger App с MySQL API"

echo.
echo ✅ Репозиторий подготовлен!
echo.
echo 📋 Следующие шаги:
echo 1. Создайте репозиторий на GitHub
echo 2. Выполните команды:
echo    git remote add origin https://github.com/YOUR_USERNAME/messenger-app.git
echo    git branch -M main
echo    git push -u origin main
echo.
echo 🚀 После этого можно деплоить на Railway!
echo.
pause
