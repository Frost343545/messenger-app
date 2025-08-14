package com.messenger.app.data.remote.config

object DatabaseConfig {
    // Railway MySQL Configuration
    const val MYSQL_HOST = "shinkansen.proxy.rlwy.net"
    const val MYSQL_PORT = 28429
    const val MYSQL_DATABASE = "railway"
    const val MYSQL_USERNAME = "root"
    const val MYSQL_PASSWORD = "QhRDpvMOnPOfPgxooBtgouZighZmZORL"
    
    // Root credentials for Railway
    const val MYSQL_ROOT_USERNAME = "root"
    const val MYSQL_ROOT_PASSWORD = "QhRDpvMOnPOfPgxooBtgouZighZmZORL"
    
    // API Configuration (Render)
    const val API_BASE_URL = "https://messenger-app-1-4fo0.onrender.com/api/"
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
    const val USE_SSL = true  // Railway использует SSL
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
