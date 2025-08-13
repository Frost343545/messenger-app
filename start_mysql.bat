@echo off
echo Запуск MySQL службы...
echo.

REM Попытка запуска MySQL службы
net start MySQL80 2>nul
if %errorlevel% equ 0 (
    echo MySQL80 запущен успешно!
    goto :end
)

net start MySQL90 2>nul
if %errorlevel% equ 0 (
    echo MySQL90 запущен успешно!
    goto :end
)

net start MySQL 2>nul
if %errorlevel% equ 0 (
    echo MySQL запущен успешно!
    goto :end
)

echo.
echo Не удалось найти MySQL службу.
echo Попробуйте запустить вручную:
echo 1. Нажмите Win + R
echo 2. Введите services.msc
echo 3. Найдите MySQL службу
echo 4. Правый клик - Запустить
echo.
pause

:end
echo.
echo Нажмите любую клавишу для выхода...
pause >nul
