# 🌐 Настройка AWS RDS MySQL для онлайн Messenger

## 🚀 Создание онлайн базы данных

### **1. AWS RDS MySQL (Рекомендуется)**

#### **Преимущества:**
- ✅ **Высокая доступность** (99.95%)
- ✅ **Автоматическое резервное копирование**
- ✅ **Масштабируемость**
- ✅ **Безопасность** (VPC, Security Groups)
- ✅ **Мониторинг** и логирование

#### **Стоимость:** ~$15-30/месяц для t3.micro

#### **Пошаговая настройка:**

1. **Войдите в AWS Console**
   - Перейдите на https://aws.amazon.com
   - Создайте аккаунт или войдите

2. **Создайте RDS Instance**
   - Перейдите в RDS → Databases → Create database
   - Выберите **MySQL 8.0**
   - Template: **Free tier** (для тестирования)

3. **Настройте параметры:**
   ```
   DB instance identifier: messenger-db
   Master username: admin
   Master password: [сложный пароль]
   DB instance class: db.t3.micro
   Storage: 20 GB
   ```

4. **Настройте сеть:**
   - VPC: Default VPC
   - Public access: Yes
   - Security group: Создайте новую
   - Port: 3306

5. **Создайте Security Group:**
   ```
   Type: MySQL/Aurora
   Protocol: TCP
   Port: 3306
   Source: 0.0.0.0/0 (или ваш IP)
   ```

### **2. Альтернативы AWS RDS:**

#### **Google Cloud SQL:**
- Стоимость: ~$20-40/месяц
- Регион: europe-west1 (Бельгия)

#### **Azure Database for MySQL:**
- Стоимость: ~$25-50/месяц
- Регион: West Europe

#### **DigitalOcean Managed MySQL:**
- Стоимость: ~$15/месяц
- Проще в настройке

## 🔧 Обновление конфигурации приложения

### **Обновите DatabaseConfig.kt:**

```kotlin
object DatabaseConfig {
    // AWS RDS MySQL Configuration
    const val MYSQL_HOST = "your-db.region.rds.amazonaws.com"
    const val MYSQL_PORT = 3306
    const val MYSQL_DATABASE = "messenger_db"
    const val MYSQL_USERNAME = "admin"
    const val MYSQL_PASSWORD = "your-password"
    
    // API Configuration
    const val API_BASE_URL = "https://your-api-domain.com/api/"
    const val USE_SSL = true
    const val API_TIMEOUT = 30L
}
```

### **Обновите Python API сервер:**

```python
MYSQL_CONFIG = {
    'host': 'your-db.region.rds.amazonaws.com',
    'port': 3306,
    'database': 'messenger_db',
    'user': 'admin',
    'password': 'your-password',
    'charset': 'utf8mb4',
    'autocommit': True,
    'ssl_disabled': False  # Включить SSL для безопасности
}
```

## 🌍 Деплой API сервера

### **Вариант 1: Heroku (Бесплатно для тестирования)**

1. **Создайте аккаунт на Heroku**
2. **Установите Heroku CLI**
3. **Создайте приложение:**
   ```bash
   heroku create your-messenger-api
   heroku addons:create heroku-postgresql:mini
   ```

### **Вариант 2: DigitalOcean App Platform**

1. **Создайте Droplet**
2. **Установите Python, Nginx**
3. **Настройте SSL сертификат**

### **Вариант 3: AWS EC2**

1. **Запустите EC2 instance**
2. **Установите Python, MySQL client**
3. **Настройте Security Groups**

## 🔐 Безопасность

### **Обязательные меры:**
- ✅ **SSL/TLS соединения**
- ✅ **Firewall правила**
- ✅ **Сильные пароли**
- ✅ **Регулярные обновления**
- ✅ **Мониторинг доступа**

### **Рекомендуемые настройки:**
- 🔒 **VPC с приватными подсетями**
- 🔒 **IAM роли для приложений**
- 🔒 **CloudWatch мониторинг**
- 🔒 **Автоматические бэкапы**

## 💰 Стоимость

### **AWS RDS MySQL (t3.micro):**
- **База данных:** $15-25/месяц
- **Хранилище:** $0.10/GB/месяц
- **Передача данных:** $0.09/GB

### **Общая стоимость:** ~$20-40/месяц

## 🎯 Следующие шаги:

1. **Выберите провайдера** (AWS RDS рекомендуется)
2. **Создайте базу данных**
3. **Обновите конфигурацию**
4. **Протестируйте подключение**
5. **Настройте мониторинг**

**Готовы создать онлайн базу данных? 🚀**
