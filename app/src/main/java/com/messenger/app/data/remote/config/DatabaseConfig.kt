package com.messenger.app.data.remote.config

object DatabaseConfig {
    // MySQL Server Configuration
    const val MYSQL_HOST = "localhost"  // или IP адрес вашего сервера
    const val MYSQL_PORT = 3306
    const val MYSQL_DATABASE = "messenger_db"
    const val MYSQL_USERNAME = "messenger_user"
    const val MYSQL_PASSWORD = "messenger_password123"
    
    // Root credentials for setup (заменить на ваши)
    const val MYSQL_ROOT_USERNAME = "root"
    const val MYSQL_ROOT_PASSWORD = "Frost113ru"
    
    // API Configuration (для REST API)
    const val API_BASE_URL = "http://localhost:8080/api/"  // Измените на ваш API сервер
    const val API_TIMEOUT = 30L  // секунды
    
    // Connection Settings
    const val CONNECTION_TIMEOUT = 10000L  // миллисекунды
    const val SOCKET_TIMEOUT = 30000L      // миллисекунды
    const val MAX_POOL_SIZE = 10
    const val MIN_POOL_SIZE = 2
    
    // Cache Settings
    const val CACHE_SIZE = 100
    const val CACHE_EXPIRY = 300000L  // 5 минут в миллисекундах
    
    // Security Settings
    const val USE_SSL = false  // Измените на true для продакшена
    const val ALLOW_PUBLIC_KEY_RETRIEVAL = true
    
    /**
     * Получить строку подключения к MySQL
     */
    fun getConnectionString(): String {
        return "jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/$MYSQL_DATABASE" +
                "?useSSL=$USE_SSL" +
                "&allowPublicKeyRetrieval=$ALLOW_PUBLIC_KEY_RETRIEVAL" +
                "&serverTimezone=UTC" +
                "&useUnicode=true" +
                "&characterEncoding=utf8"
    }
    
    /**
     * Получить URL для API
     */
    fun getApiUrl(): String {
        return API_BASE_URL
    }
    
    /**
     * Получить полный URL для конкретного эндпоинта
     */
    fun getApiEndpoint(endpoint: String): String {
        return "$API_BASE_URL$endpoint"
    }
}
